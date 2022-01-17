package com.ultreon.randomthingz.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ultreon.modlib.embedded.silentlib.util.NameUtils;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AlloySmeltingRecipeBuilder {
    private final Map<Ingredient, Integer> ingredients = new LinkedHashMap<>();
    private final ItemStack result;
    private final int processTime;

    private AlloySmeltingRecipeBuilder(ItemLike result, int count, int processTime) {
        this.result = new ItemStack(result, count);
        this.processTime = processTime;
    }

    public static AlloySmeltingRecipeBuilder builder(ItemLike result, int count, int processTime) {
        return new AlloySmeltingRecipeBuilder(result, count, processTime);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static AlloySmeltingRecipeBuilder builder(ItemMaterial result, int count, int processTime) {
        return builder(result.getIngot().get(), count, processTime);
    }

    public AlloySmeltingRecipeBuilder ingredient(Ingredient ingredient, int count) {
        ingredients.put(ingredient, count);
        return this;
    }

    public AlloySmeltingRecipeBuilder ingredient(ItemLike item, int count) {
        return ingredient(Ingredient.of(item), count);
    }

    public AlloySmeltingRecipeBuilder ingredient(Tag<Item> tag, int count) {
        return ingredient(Ingredient.of(tag), count);
    }

    public AlloySmeltingRecipeBuilder ingredient(ItemMaterial metal, int count) {
        return ingredient(metal.getSmeltables(), count);
    }

    public void build(Consumer<FinishedRecipe> consumer) {
        ResourceLocation resultId = NameUtils.fromItem(result);
        ResourceLocation id = new ResourceLocation(
                "minecraft".equals(resultId.getNamespace()) ? RandomThingz.MOD_ID : resultId.getNamespace(),
                "alloy_smelting/" + resultId.getPath());
        build(consumer, id);
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new Result(id, this));
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final AlloySmeltingRecipeBuilder builder;

        public Result(ResourceLocation id, AlloySmeltingRecipeBuilder builder) {
            this.id = id;
            this.builder = builder;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("process_time", builder.processTime);

            JsonArray ingredients = new JsonArray();
            builder.ingredients.forEach((ingredient, count) ->
                    ingredients.add(serializeIngredient(ingredient, count)));
            json.add("ingredients", ingredients);

            JsonObject result = new JsonObject();
            result.addProperty("item", NameUtils.fromItem(builder.result).toString());
            if (builder.result.getCount() > 1) {
                result.addProperty("count", builder.result.getCount());
            }
            json.add("result", result);
        }

        private JsonElement serializeIngredient(Ingredient ingredient, int count) {
            JsonObject json = new JsonObject();
            json.add("value", ingredient.toJson());
            json.addProperty("count", count);
            return json;
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.ALLOY_SMELTING.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
