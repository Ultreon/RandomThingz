package com.ultreon.randomthingz.data.recipes;

import com.google.gson.JsonObject;
import com.ultreon.modlib.api.crafting.recipe.fluid.FluidIngredient;
import com.ultreon.modlib.silentlib.util.NameUtils;
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

public final class InfusingRecipeBuilder {
    private final Ingredient ingredient;
    private final FluidIngredient fluid;
    private final ItemStack result;
    private final int processTime;

    private InfusingRecipeBuilder(Ingredient ingredient, FluidIngredient fluid, ItemStack result, int processTime) {
        this.ingredient = ingredient;
        this.fluid = fluid;
        this.result = result;
        this.processTime = processTime;
    }

    public static InfusingRecipeBuilder builder(ItemLike result, int count, int processTime, Ingredient ingredient, FluidIngredient fluid) {
        return new InfusingRecipeBuilder(ingredient, fluid, new ItemStack(result, count), processTime);
    }

    public static InfusingRecipeBuilder builder(ItemLike result, int count, int processTime, Tag<Item> ingredient, FluidIngredient fluid) {
        return builder(result, count, processTime, Ingredient.of(ingredient), fluid);
    }

    public static InfusingRecipeBuilder builder(ItemLike result, int count, int processTime, ItemLike ingredient, FluidIngredient fluid) {
        return builder(result, count, processTime, Ingredient.of(ingredient), fluid);
    }

    public void build(Consumer<FinishedRecipe> consumer) {
        ResourceLocation resultId = NameUtils.fromItem(result);
        ResourceLocation id = new ResourceLocation(
                "minecraft".equals(resultId.getNamespace()) ? RandomThingz.MOD_ID : resultId.getNamespace(),
                "infusing/" + resultId.getPath());
        build(consumer, id);
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new Result(id, this));
    }

    @SuppressWarnings("ClassCanBeRecord")
    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final InfusingRecipeBuilder builder;

        public Result(ResourceLocation id, InfusingRecipeBuilder builder) {
            this.id = id;
            this.builder = builder;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("process_time", builder.processTime);

            json.add("ingredient", builder.ingredient.toJson());
            json.add("fluid", builder.fluid.serialize());

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
            return ModRecipes.INFUSING.get();
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
