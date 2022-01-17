package com.ultreon.randomthingz.item.crafting;

import com.google.gson.JsonObject;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

@RequiredArgsConstructor
public class DryingRecipe implements Recipe<Container> {
    private final ResourceLocation recipeId;
    @Getter
    private int processTime;
    @Getter
    private Ingredient ingredient;
    private ItemStack result;

    @Override
    public boolean matches(Container inv, Level dimensionIn) {
        ItemStack stack = inv.getItem(0);
        return ingredient.test(stack);
    }

    @Override
    public ItemStack assemble(Container inv) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.DRYING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.Types.DRYING;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<DryingRecipe> {
        @Override
        public DryingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            DryingRecipe recipe = new DryingRecipe(recipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time", 400);
            recipe.ingredient = Ingredient.fromJson(json.get("ingredient"));
            recipe.result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return recipe;
        }

        @Override
        public DryingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            DryingRecipe recipe = new DryingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = Ingredient.fromNetwork(buffer);
            recipe.result = buffer.readItem();
            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, DryingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
