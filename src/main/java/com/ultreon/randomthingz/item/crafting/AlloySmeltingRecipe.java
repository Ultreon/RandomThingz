package com.ultreon.randomthingz.item.crafting;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ultreon.randomthingz.block.machines.IMachineInventory;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import com.ultreon.randomthingz.util.InventoryUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.item.crafting.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AlloySmeltingRecipe implements Recipe<IMachineInventory> {
    private final ResourceLocation recipeId;
    private final Map<Ingredient, Integer> ingredients = new LinkedHashMap<>();
    @Getter
    private int processTime;
    @Getter
    private ItemStack result;

    public void consumeIngredients(IMachineInventory inv) {
        ingredients.forEach(((ingredient, count) -> InventoryUtils.consumeItems(inv, ingredient, count)));
    }

    public Map<Ingredient, Integer> getIngredientMap() {
        return ImmutableMap.copyOf(ingredients);
    }

    @Override
    public boolean matches(IMachineInventory inv, Level dimensionIn) {
        for (Ingredient ingredient : ingredients.keySet()) {
            int required = ingredients.get(ingredient);
            int found = InventoryUtils.getTotalCount(inv, ingredient);
            if (found < required) {
                return false;
            }
        }

        // Check for non-matching items
        for (int i = 0; i < inv.getInputSlotCount(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                boolean foundMatch = false;
                for (Ingredient ingredient : ingredients.keySet()) {
                    if (ingredient.test(stack)) {
                        foundMatch = true;
                        break;
                    }
                }
                if (!foundMatch) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(IMachineInventory inv) {
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
        return ModRecipes.ALLOY_SMELTING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.Types.ALLOY_SMELTING;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AlloySmeltingRecipe> {
        private static Ingredient deserializeIngredient(JsonElement element) {
            if (element.isJsonObject()) {
                JsonObject json = element.getAsJsonObject();
                if (json.has("value"))
                    return Ingredient.fromJson(json.get("value"));
                if (json.has("values"))
                    return Ingredient.fromJson(json.get("values"));
            }
            return Ingredient.fromJson(element);
        }

        @Override
        public AlloySmeltingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            AlloySmeltingRecipe recipe = new AlloySmeltingRecipe(recipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time", 400);
            recipe.result = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result"));

            GsonHelper.getAsJsonArray(json, "ingredients").forEach(element -> {
                Ingredient ingredient = deserializeIngredient(element);
                int count = GsonHelper.getAsInt(element.getAsJsonObject(), "count", 1);
                recipe.ingredients.put(ingredient, count);
            });

            return recipe;
        }

        @Override
        public AlloySmeltingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            AlloySmeltingRecipe recipe = new AlloySmeltingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.result = buffer.readItem();

            int ingredientCount = buffer.readByte();
            for (int i = 0; i < ingredientCount; ++i) {
                Ingredient ingredient = Ingredient.fromNetwork(buffer);
                int count = buffer.readByte();
                recipe.ingredients.put(ingredient, count);
            }

            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlloySmeltingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            buffer.writeItem(recipe.result);

            buffer.writeByte(recipe.ingredients.size());
            recipe.ingredients.forEach((ingredient, count) -> {
                ingredient.toNetwork(buffer);
                buffer.writeByte(count);
            });
        }
    }
}
