package com.ultreon.randomthingz.item.crafting;

import com.google.gson.JsonObject;
import com.qsoftware.modlib.api.crafting.recipe.fluid.FluidIngredient;
import com.qsoftware.modlib.api.crafting.recipe.fluid.IFluidInventory;
import com.qsoftware.modlib.api.crafting.recipe.fluid.IFluidRecipe;
import com.ultreon.randomthingz.block.machines.infuser.InfuserTileEntity;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class InfusingRecipe implements IFluidRecipe<IFluidInventory> {
    private final ResourceLocation recipeId;
    private final int processTime;
    private final Ingredient ingredient;
    private final FluidIngredient fluid;
    private final ItemStack result;

    public int getProcessTime() {
        return processTime;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public FluidIngredient getFluidIngredient() {
        return this.fluid;
    }

    @Override
    public List<FluidStack> getFluidResults(IFluidInventory inv) {
        return Collections.emptyList();
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return Collections.emptyList();
    }

    @Override
    public List<FluidIngredient> getFluidIngredients() {
        return Collections.singletonList(this.fluid);
    }

    @Override
    public boolean matches(IFluidInventory inv, Level dimensionIn) {
        FluidStack fluidInTank = inv.getFluidInTank(0);
        ItemStack input = inv.getItem(InfuserTileEntity.SLOT_ITEM_IN);
        return this.fluid.test(fluidInTank) && this.ingredient.test(input);
    }

    @Override
    public ItemStack getResultItem() {
        return this.result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.INFUSING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.Types.INFUSING;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<InfusingRecipe> {
        @Override
        public InfusingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            int processTime = GsonHelper.getAsInt(json, "process_time");
            Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
            FluidIngredient fluid = FluidIngredient.deserialize(json.getAsJsonObject("fluid"));
            ItemStack result = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new InfusingRecipe(recipeId, processTime, ingredient, fluid, result);
        }

        @Nullable
        @Override
        public InfusingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int processTime = buffer.readVarInt();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            FluidIngredient fluid = FluidIngredient.read(buffer);
            ItemStack result = buffer.readItem();
            return new InfusingRecipe(recipeId, processTime, ingredient, fluid, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, InfusingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.toNetwork(buffer);
            recipe.fluid.write(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
