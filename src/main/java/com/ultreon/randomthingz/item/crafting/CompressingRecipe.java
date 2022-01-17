package com.ultreon.randomthingz.item.crafting;

import com.google.gson.JsonElement;
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
public class CompressingRecipe implements Recipe<Container> {
    private final ResourceLocation recipeId;
    @Getter
    private int processTime;
    @Getter
    private Ingredient ingredient;
    @Getter
    private int ingredientCount;
    private ItemStack result;

    @Override
    public boolean matches(Container inv, Level dimensionIn) {
        ItemStack stack = inv.getItem(0);
        return ingredient.test(stack) && stack.getCount() >= ingredientCount;
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
        return ModRecipes.COMPRESSING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.Types.COMPRESSING;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CompressingRecipe> {
        @Override
        public CompressingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            CompressingRecipe recipe = new CompressingRecipe(recipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time", 400);
            JsonElement ingredientJson = json.get("ingredient");
            if (ingredientJson.isJsonObject() && ingredientJson.getAsJsonObject().has("value")) {
                JsonObject obj = ingredientJson.getAsJsonObject();
                recipe.ingredient = Ingredient.fromJson(obj.get("value"));
                recipe.ingredientCount = GsonHelper.getAsInt(obj, "count", 1);
            } else {
                recipe.ingredient = Ingredient.fromJson(ingredientJson);
                recipe.ingredientCount = GsonHelper.getAsInt(ingredientJson.getAsJsonObject(), "count", 1);
            }
            recipe.result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return recipe;
        }

        @Override
        public CompressingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            CompressingRecipe recipe = new CompressingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = Ingredient.fromNetwork(buffer);
            recipe.ingredientCount = buffer.readByte();
            recipe.result = buffer.readItem();
            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CompressingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeByte(recipe.ingredientCount);
            buffer.writeItem(recipe.result);
        }
    }
}
