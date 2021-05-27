package com.qtech.randomthingz.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.qsoftware.modlib.silentlib.util.NameUtils;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.item.crafting.common.ModRecipes;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

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

    public void build(Consumer<IFinishedRecipe> consumer) {
        ResourceLocation resultId = NameUtils.from(result);
        ResourceLocation id = new ResourceLocation(
                "minecraft".equals(resultId.getNamespace()) ? RandomThingz.MOD_ID : resultId.getNamespace(),
                "arcane_escalating/" + resultId.getPath());
        build(consumer, id);
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new Result(id, this));
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final ArcaneEscalatingRecipeBuilder builder;

        public Result(ResourceLocation id, ArcaneEscalatingRecipeBuilder builder) {
            this.id = id;
            this.builder = builder;
        }

        @Override
        public void serialize(JsonObject json) {
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
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return ModRecipes.ARCANE_ESCALATING.get();
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }
}
