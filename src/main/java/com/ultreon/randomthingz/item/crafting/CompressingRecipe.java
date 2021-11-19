package com.ultreon.randomthingz.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

@RequiredArgsConstructor
public class CompressingRecipe implements IRecipe<IInventory> {
    private final ResourceLocation recipeId;
    @Getter private int processTime;
    @Getter private Ingredient ingredient;
    @Getter private int ingredientCount;
    private ItemStack result;

    @Override
    public boolean matches(IInventory inv, World dimensionIn) {
        ItemStack stack = inv.getStackInSlot(0);
        return ingredient.test(stack) && stack.getCount() >= ingredientCount;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.COMPRESSING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.Types.COMPRESSING;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CompressingRecipe> {
        @Override
        public CompressingRecipe read(ResourceLocation recipeId, JsonObject json) {
            CompressingRecipe recipe = new CompressingRecipe(recipeId);
            recipe.processTime = JSONUtils.getInt(json, "process_time", 400);
            JsonElement ingredientJson = json.get("ingredient");
            if (ingredientJson.isJsonObject() && ingredientJson.getAsJsonObject().has("value")) {
                JsonObject obj = ingredientJson.getAsJsonObject();
                recipe.ingredient = Ingredient.deserialize(obj.get("value"));
                recipe.ingredientCount = JSONUtils.getInt(obj, "count", 1);
            } else {
                recipe.ingredient = Ingredient.deserialize(ingredientJson);
                recipe.ingredientCount = JSONUtils.getInt(ingredientJson.getAsJsonObject(), "count", 1);
            }
            recipe.result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return recipe;
        }

        @Override
        public CompressingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            CompressingRecipe recipe = new CompressingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = Ingredient.read(buffer);
            recipe.ingredientCount = buffer.readByte();
            recipe.result = buffer.readItemStack();
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, CompressingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.write(buffer);
            buffer.writeByte(recipe.ingredientCount);
            buffer.writeItemStack(recipe.result);
        }
    }
}
