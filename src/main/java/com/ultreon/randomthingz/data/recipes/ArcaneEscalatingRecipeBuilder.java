package com.ultreon.randomthingz.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.qsoftware.modlib.silentlib.util.NameUtils;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ArcaneEscalatingRecipeBuilder {
    private final Enchantment result;
    private final int processTime;
    private final List<ItemStack> items = new ArrayList<>();

    private ArcaneEscalatingRecipeBuilder(Enchantment result, int processTime) {
        this.result = result;
        this.processTime = processTime;
    }

    public static ArcaneEscalatingRecipeBuilder builder(Enchantment result, int processTime) {
        return new ArcaneEscalatingRecipeBuilder(result, processTime);
    }

    public ArcaneEscalatingRecipeBuilder item(Item item, int count) {
        items.add(new ItemStack(item, count));
        return this;
    }

    public ArcaneEscalatingRecipeBuilder item(ItemStack stack) {
        items.add(stack.copy());
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumer) {
        ResourceLocation resultId = NameUtils.from(result);
        ResourceLocation id = new ResourceLocation(
                "minecraft".equals(resultId.getNamespace()) ? RandomThingz.MOD_ID : resultId.getNamespace(),
                "arcane_escalating/" + resultId.getPath());
        build(consumer, id);
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new Result(id, this));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ArcaneEscalatingRecipeBuilder builder;

        public Result(ResourceLocation id, ArcaneEscalatingRecipeBuilder builder) {
            this.id = id;
            this.builder = builder;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("process_time", builder.processTime);

            if (builder.items.isEmpty()) {
                throw new IllegalStateException("Items list should be non-empty. Add items using the item(...) method in the builder.");
            }

            if (builder.items.size() == 1) {
                ItemStack stack = builder.items.get(0);
                if (stack.getCount() == 1) {
                    json.addProperty("item", NameUtils.fromItem(stack.getItem()).toString());
                } else {
                    JsonObject obj = new JsonObject();
                    obj.addProperty("name", NameUtils.fromItem(stack.getItem()).toString());
                    obj.addProperty("count", stack.getCount());
                    json.add("item", obj);
                }
            } else {
                JsonArray array = new JsonArray();
                for (ItemStack stack : builder.items) {
                    if (stack.getCount() == 1) {
                        array.add(NameUtils.fromItem(stack.getItem()).toString());
                    } else {
                        JsonObject obj = new JsonObject();
                        obj.addProperty("name", NameUtils.fromItem(stack.getItem()).toString());
                        obj.addProperty("count", stack.getCount());
                        array.add(obj);
                    }
                }
                json.add("items", array);
            }

            json.addProperty("result", NameUtils.from(builder.result).toString());
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.ARCANE_ESCALATING.get();
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
