package com.qtech.randomthingz.item.crafting;

import com.google.gson.JsonObject;
import com.qsoftware.modlib.api.crafting.recipe.fluid.FluidIngredient;
import com.qsoftware.modlib.api.crafting.recipe.fluid.IFluidInventory;
import com.qsoftware.modlib.api.crafting.recipe.fluid.IFluidRecipe;
import com.qtech.randomthingz.block.machines.infuser.InfuserTileEntity;
import com.qtech.randomthingz.item.crafting.common.ModRecipes;
import lombok.RequiredArgsConstructor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
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
    public boolean matches(IFluidInventory inv, World dimensionIn) {
        FluidStack fluidInTank = inv.getFluidInTank(0);
        ItemStack input = inv.getStackInSlot(InfuserTileEntity.SLOT_ITEM_IN);
        return this.fluid.test(fluidInTank) && this.ingredient.test(input);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.INFUSING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.Types.INFUSING;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<InfusingRecipe> {
        @Override
        public InfusingRecipe read(ResourceLocation recipeId, JsonObject json) {
            int processTime = JSONUtils.getInt(json, "process_time");
            Ingredient ingredient = Ingredient.deserialize(json.get("ingredient"));
            FluidIngredient fluid = FluidIngredient.deserialize(json.getAsJsonObject("fluid"));
            ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new InfusingRecipe(recipeId, processTime, ingredient, fluid, result);
        }

        @Nullable
        @Override
        public InfusingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int processTime = buffer.readVarInt();
            Ingredient ingredient = Ingredient.read(buffer);
            FluidIngredient fluid = FluidIngredient.read(buffer);
            ItemStack result = buffer.readItemStack();
            return new InfusingRecipe(recipeId, processTime, ingredient, fluid, result);
        }

        @Override
        public void write(PacketBuffer buffer, InfusingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.write(buffer);
            recipe.fluid.write(buffer);
            buffer.writeItemStack(recipe.result);
        }
    }
}
