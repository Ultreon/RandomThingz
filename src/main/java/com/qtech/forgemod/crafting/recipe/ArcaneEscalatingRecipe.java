package com.qtech.forgemod.crafting.recipe;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.qtech.forgemod.init.ModRecipes;
import com.qtech.forgemod.block.machines.IMachineInventory;
import com.qtech.forgemod.util.InventoryUtils;
import com.qsoftware.modlib.silentlib.util.NameUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.*;

@RequiredArgsConstructor
public class ArcaneEscalatingRecipe implements IRecipe<IMachineInventory> {
    private final ResourceLocation recipeId;
    private final Map<Ingredient, Integer> ingredients = new LinkedHashMap<>();
    @Getter private int processTime;
    @Getter private Enchantment result;
    private List<ItemStack> input;

    public void consumeIngredients(IMachineInventory inv) {
        ingredients.forEach(((ingredient, count) -> InventoryUtils.consumeItems(inv, ingredient, count)));
    }

    public Map<Ingredient, Integer> getIngredientMap() {
        return ImmutableMap.copyOf(ingredients);
    }

    @Override
    public boolean matches(IMachineInventory inv, World dimensionIn) {
        for (Ingredient ingredient : ingredients.keySet()) {
            int required = ingredients.get(ingredient);
            int found = InventoryUtils.getTotalCount(inv, ingredient);
            if (found < required) {
                return false;
            }
        }

        // Check for non-matching items
        for (int i = 0; i < inv.getInputSlotCount(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
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
    public ItemStack getCraftingResult(IMachineInventory inv) {
        ItemStack out = new ItemStack(Items.ENCHANTED_BOOK);
        out.addEnchantment(result, 1);
        return out;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        ItemStack out = new ItemStack(Items.ENCHANTED_BOOK);
        out.addEnchantment(result, 1);
        return out;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.ARCANE_ESCALATING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.Types.ARCANE_ESCALATING;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ArcaneEscalatingRecipe> {
        @SuppressWarnings("unused")
        @Deprecated
        @Nullable
        private static Item deserializeItem(JsonElement element) {
            if (element.isJsonObject()) {
                JsonObject json = element.getAsJsonObject();
                if (json.has("item")) {
                    return ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.getAsJsonPrimitive("item").getAsString()));
                }
            } else if (element.isJsonPrimitive()) {
                JsonPrimitive json = element.getAsJsonPrimitive();
                return ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.getAsString()));
            }
            return null;
        }

        @Nullable
        private static Enchantment deserializeEnchantment(JsonElement element) {
            if (element.isJsonObject()) {
                JsonObject json = element.getAsJsonObject();
                if (json.has("enchantment")) {
                    return ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(json.getAsJsonPrimitive("enchantment").getAsString()));
                }
            } else if (element.isJsonPrimitive()) {
                JsonPrimitive json = element.getAsJsonPrimitive();
                return ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(json.getAsString()));
            }
            return null;
        }

        @Override
        public ArcaneEscalatingRecipe read(ResourceLocation recipeId, JsonObject json) {
            ArcaneEscalatingRecipe recipe = new ArcaneEscalatingRecipe(recipeId);
            recipe.processTime = JSONUtils.getInt(json, "process_time", 400);

            List<ItemStack> stacks = new ArrayList<>();

            if (json.has("item") && json.has("items")) {
                throw new JsonSyntaxException("Expected item or items, not both.");
            }

            JsonArray multiItems = JSONUtils.getJsonArray(json, "items", null);
            if (multiItems == null) {
                ItemStack stack;

                if (json.has("item")) {
                    JsonElement element = json.get("item");
                    if (element.isJsonPrimitive()) {
                        JsonPrimitive primitive = element.getAsJsonPrimitive();
                        if (primitive.isString()) {
                            String name = primitive.getAsString();
                            Item value = ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
                            stack = new ItemStack(value);
                        } else {
                            throw new JsonSyntaxException("Missing item, expected string or object.");
                        }
                    } else if (element.isJsonObject()) {
                        JsonObject object = element.getAsJsonObject();
                        int count = JSONUtils.getInt(object, "count", 1);
                        String name = JSONUtils.getString(object, "name");
                        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));

                        stack = new ItemStack(item, count);
                    } else {
                        throw new JsonSyntaxException("Missing item, expected string or object.");
                    }
                } else {
                    throw new JsonSyntaxException("Missing item, expected string or object. Missing items, expected array.");
                }

                stacks.add(stack);
            } else {
                for (JsonElement element : multiItems) {
                    ItemStack stack;
                    if (element.isJsonPrimitive()) {
                        JsonPrimitive primitive = element.getAsJsonPrimitive();
                        if (primitive.isString()) {
                            String name = primitive.getAsString();
                            Item value = ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
                            stack = new ItemStack(value);
                        } else {
                            throw new JsonSyntaxException("Missing item, expected string or object.");
                        }
                    } else if (element.isJsonObject()) {
                        JsonObject object = element.getAsJsonObject();
                        int count = JSONUtils.getInt(object, "count", 1);
                        String name = JSONUtils.getString(object, "name");
                        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));

                        stack = new ItemStack(item, count);
                    } else {
                        throw new JsonSyntaxException("Missing item, expected string or object.");
                    }

                    stacks.add(stack);
                }
            }

            recipe.input = stacks;
            recipe.result = deserializeEnchantment(json.getAsJsonPrimitive("result"));
//            recipe.input = deserializeItem(json.getAsJsonPrimitive("input"));

            return recipe;
        }

        @Override
        public ArcaneEscalatingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            ArcaneEscalatingRecipe recipe = new ArcaneEscalatingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.result = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(buffer.readString()));
            int itemCount = buffer.readInt();

            List<ItemStack> stacks = new ArrayList<>();
            for (int i = 0; i < itemCount; i++) {
                ItemStack stack;

                String name = buffer.readString(128);
                int count = buffer.readInt();

                stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(name)), count);
                stacks.add(stack);
            }

            recipe.input = stacks;

            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, ArcaneEscalatingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            buffer.writeString(NameUtils.from(recipe.result).toString());

            buffer.writeInt(recipe.input.size());
            for (ItemStack stack : recipe.input) {
                buffer.writeString(NameUtils.fromItem(stack).toString(), 128);
                buffer.writeInt(stack.getCount());
            }
        }
    }
}
