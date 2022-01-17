package com.ultreon.randomthingz.data.recipes;

import com.google.gson.JsonObject;
import com.ultreon.modlib.embedded.silentlib.util.NameUtils;
import com.ultreon.randomthingz.RandomThingz;
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

import java.util.function.Consumer;

public class CompressingRecipeBuilder {
    private final Ingredient ingredient;
    private final int ingredientCount;
    private final ItemStack result;
    private final int processTime;

    private CompressingRecipeBuilder(Ingredient ingredient, int ingredientCount, ItemLike result, int resultCount, int processTime) {
        this.ingredient = ingredient;
        this.ingredientCount = ingredientCount;
        this.result = new ItemStack(result, resultCount);
        this.processTime = processTime;
    }

    public static CompressingRecipeBuilder builder(Ingredient ingredient, int ingredientCount, ItemLike result, int resultCount, int processTime) {
        return new CompressingRecipeBuilder(ingredient, ingredientCount, result, resultCount, processTime);
    }

    public static CompressingRecipeBuilder builder(Tag<Item> ingredient, int ingredientCount, ItemLike result, int resultCount, int processTime) {
        return builder(Ingredient.of(ingredient), ingredientCount, result, resultCount, processTime);
    }

    public static CompressingRecipeBuilder builder(ItemLike ingredient, int ingredientCount, ItemLike result, int resultCount, int processTime) {
        return builder(Ingredient.of(ingredient), ingredientCount, result, resultCount, processTime);
    }

    public void build(Consumer<FinishedRecipe> consumer) {
        ResourceLocation resultId = NameUtils.fromItem(result);
        ResourceLocation id = new ResourceLocation(
                "minecraft".equals(resultId.getNamespace()) ? RandomThingz.MOD_ID : resultId.getNamespace(),
                "compressing/" + resultId.getPath());
        build(consumer, id);
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new Result(id, this));
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final CompressingRecipeBuilder builder;

        public Result(ResourceLocation id, CompressingRecipeBuilder builder) {
            this.id = id;
            this.builder = builder;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("process_time", builder.processTime);

            JsonObject ingredient = new JsonObject();
            ingredient.add("value", builder.ingredient.toJson());
            ingredient.addProperty("count", builder.ingredientCount);
            json.add("ingredient", ingredient);

            JsonObject result = new JsonObject();
            result.addProperty("item", NameUtils.fromItem(builder.result).toString());
            if (builder.result.getCount() > 1) {
                result.addProperty("count", builder.result.getCount());
            }
            json.add("result", result);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.COMPRESSING.get();
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
