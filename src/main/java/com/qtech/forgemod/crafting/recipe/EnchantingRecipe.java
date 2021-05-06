package com.qtech.forgemod.crafting.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.qtech.forgemod.init.ModRecipes;
import com.qtech.forgemod.block.machines.IMachineInventory;
import com.qtech.forgemod.util.InventoryUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Objects;

@RequiredArgsConstructor
public class EnchantingRecipe implements IRecipe<IMachineInventory> {
    private final ResourceLocation recipeId;
    @Getter private Item input;
    @Getter private int processTime;
    private Enchantment result;

    public void consumeIngredients(IMachineInventory inv) {
        InventoryUtils.consumeItems(inv, (stack) -> stack.getItem() == input, 1);
    }

    @Override
    public boolean matches(IMachineInventory inv, World dimensionIn) {
        int required = 1;
        int found = InventoryUtils.getTotalCount(inv, (stack) -> stack.getItem() == input);
        if (found < required) {
            return false;
        }

        // Check for non-matching items
        for (int i = 0; i < inv.getInputSlotCount(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
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
    public ItemStack getCraftingResult(IMachineInventory inv) {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        stack.addEnchantment(result, 1);
        return stack;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        stack.addEnchantment(result, 1);
        return stack;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.ENCHANTING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.Types.ENCHANTING;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<EnchantingRecipe> {
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
        public EnchantingRecipe read(ResourceLocation recipeId, JsonObject json) {
            EnchantingRecipe recipe = new EnchantingRecipe(recipeId);
            recipe.processTime = JSONUtils.getInt(json, "process_time", 400);
            recipe.result = deserializeEnchantment(json.getAsJsonPrimitive("result"));

            JSONUtils.getJsonArray(json, "input").forEach(element -> {
                recipe.input = deserializeItem(element);
            });

            return recipe;
        }

        @Override
        public EnchantingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            EnchantingRecipe recipe = new EnchantingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.result = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(buffer.readString()));
            recipe.input = ForgeRegistries.ITEMS.getValue(new ResourceLocation(buffer.readString()));

            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, EnchantingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            buffer.writeString(Objects.requireNonNull(recipe.result.getRegistryName()).toString());
            buffer.writeString(Objects.requireNonNull(recipe.input.getRegistryName()).toString());
        }
    }
}
