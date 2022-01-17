package com.ultreon.randomthingz.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@RequiredArgsConstructor
public class EnchantingRecipe implements Recipe<IMachineInventory> {
    private final ResourceLocation recipeId;
    @Getter
    private Item input;
    @Getter
    private int processTime;
    private Enchantment result;

    public void consumeIngredients(IMachineInventory inv) {
        InventoryUtils.consumeItems(inv, (stack) -> stack.getItem() == input, 1);
    }

    @Override
    public boolean matches(IMachineInventory inv, Level dimensionIn) {
        int required = 1;
        int found = InventoryUtils.getTotalCount(inv, (stack) -> stack.getItem() == input);
        if (found < required) {
            return false;
        }

        // Check for non-matching items
        for (int i = 0; i < inv.getInputSlotCount(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                boolean foundMatch = false;
                if (input == stack.getItem()) {
                    foundMatch = true;
                    break;
                }
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(IMachineInventory inv) {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        stack.enchant(result, 1);
        return stack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        stack.enchant(result, 1);
        return stack;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ENCHANTING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.Types.ENCHANTING;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<EnchantingRecipe> {
        @Nullable
        private static Item deserializeItem(JsonElement element) {
            if (element.isJsonObject()) {
                JsonObject json = element.getAsJsonObject();
                if (json.has("item")) {
                    return ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.getAsJsonPrimitive("item").getAsString()));
                }
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
        public EnchantingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            EnchantingRecipe recipe = new EnchantingRecipe(recipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time", 400);
            recipe.result = deserializeEnchantment(json.getAsJsonPrimitive("result"));

            GsonHelper.getAsJsonArray(json, "input").forEach(element -> recipe.input = deserializeItem(element));

            return recipe;
        }

        @Override
        public EnchantingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            EnchantingRecipe recipe = new EnchantingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.result = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(buffer.readUtf()));
            recipe.input = ForgeRegistries.ITEMS.getValue(new ResourceLocation(buffer.readUtf()));

            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, EnchantingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            buffer.writeUtf(Objects.requireNonNull(recipe.result.getRegistryName()).toString());
            buffer.writeUtf(Objects.requireNonNull(recipe.input.getRegistryName()).toString());
        }
    }
}
