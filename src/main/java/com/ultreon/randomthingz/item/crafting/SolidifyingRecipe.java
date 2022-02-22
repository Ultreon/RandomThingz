package com.ultreon.randomthingz.item.crafting;

import com.google.gson.JsonObject;
import com.ultreon.modlib.api.crafting.recipe.fluid.FluidInventory;
import com.ultreon.modlib.api.crafting.recipe.fluid.FluidRecipe;
import com.ultreon.modlib.api.crafting.recipe.fluid.FluidIngredient;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class SolidifyingRecipe implements FluidRecipe<FluidInventory> {
    private final ResourceLocation recipeId;
    @Getter
    private int processTime;
    @Getter
    private FluidIngredient ingredient;
    private ItemStack result;

    @Override
    public boolean matches(FluidInventory inv, Level dimensionIn) {
        return ingredient.test(inv.getFluidInTank(0));
    }

    @Override
    public List<FluidStack> getFluidResults(FluidInventory inv) {
        return getFluidOutputs();
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return Collections.emptyList();
    }

    @Override
    public List<FluidIngredient> getFluidIngredients() {
        return Collections.singletonList(ingredient);
    }

    @Override
    public ItemStack assemble(FluidInventory inv) {
        return getResultItem();
    }

    @Override
    public ItemStack getResultItem() {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SOLIDIFYING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.Types.SOLIDIFYING;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SolidifyingRecipe> {
        @Override
        public SolidifyingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            SolidifyingRecipe recipe = new SolidifyingRecipe(recipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time");
            recipe.ingredient = FluidIngredient.deserialize(json.getAsJsonObject("ingredient"));
            recipe.result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return recipe;
        }

        @Nullable
        @Override
        public SolidifyingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            SolidifyingRecipe recipe = new SolidifyingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = FluidIngredient.read(buffer);
            recipe.result = buffer.readItem();
            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SolidifyingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.write(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
