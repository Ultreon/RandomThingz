package com.ultreon.randomthingz.item.crafting;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.ultreon.modlib.embedded.silentlib.util.NameUtils;
import com.ultreon.randomthingz.block.machines.IMachineInventory;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import com.ultreon.randomthingz.util.InventoryUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ArcaneEscalatingRecipe implements Recipe<IMachineInventory> {
    private final ResourceLocation recipeId;
    private final Map<Ingredient, Integer> ingredients = new LinkedHashMap<>();
    @Getter
    private int processTime;
    @Getter
    private Enchantment result;
    private List<ItemStack> input;

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
        ItemStack out = new ItemStack(Items.ENCHANTED_BOOK);
        out.enchant(result, 1);
        return out;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        ItemStack out = new ItemStack(Items.ENCHANTED_BOOK);
        out.enchant(result, 1);
        return out;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ARCANE_ESCALATING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.Types.ARCANE_ESCALATING;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ArcaneEscalatingRecipe> {
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
        public ArcaneEscalatingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ArcaneEscalatingRecipe recipe = new ArcaneEscalatingRecipe(recipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time", 400);

            List<ItemStack> stacks = new ArrayList<>();

            if (json.has("item") && json.has("items")) {
                throw new JsonSyntaxException("Expected item or items, not both.");
            }

            JsonArray multiItems = GsonHelper.getAsJsonArray(json, "items", null);
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
                        int count = GsonHelper.getAsInt(object, "count", 1);
                        String name = GsonHelper.getAsString(object, "name");
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
                        int count = GsonHelper.getAsInt(object, "count", 1);
                        String name = GsonHelper.getAsString(object, "name");
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

            return recipe;
        }

        @Override
        public ArcaneEscalatingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            ArcaneEscalatingRecipe recipe = new ArcaneEscalatingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.result = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(buffer.readUtf()));
            int itemCount = buffer.readInt();

            List<ItemStack> stacks = new ArrayList<>();
            for (int i = 0; i < itemCount; i++) {
                ItemStack stack;

                String name = buffer.readUtf(128);
                int count = buffer.readInt();

                stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(name)), count);
                stacks.add(stack);
            }

            recipe.input = stacks;

            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ArcaneEscalatingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            buffer.writeUtf(NameUtils.from(recipe.result).toString());

            buffer.writeInt(recipe.input.size());
            for (ItemStack stack : recipe.input) {
                buffer.writeUtf(NameUtils.fromItem(stack).toString(), 128);
                buffer.writeInt(stack.getCount());
            }
        }
    }
}
