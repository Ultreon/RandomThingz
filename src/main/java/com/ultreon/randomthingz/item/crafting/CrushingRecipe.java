package com.ultreon.randomthingz.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.ultreon.modlib.silentutils.MathUtils;
import com.ultreon.randomthingz.block.machines.MachineBlockEntity;
import com.ultreon.randomthingz.init.ModMachineUpgrades;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import com.ultreon.randomthingz.item.upgrade.MachineUpgrade;
import com.ultreon.randomthingz.util.Constants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.*;

@RequiredArgsConstructor
public class CrushingRecipe implements Recipe<Container> {
    private final ResourceLocation recipeId;
    private final Map<ItemStack, Float> results = new LinkedHashMap<>();
    @Getter
    private int processTime;
    @Getter
    private Ingredient ingredient;

    /**
     * Get results of crushing. Some results may have a limited chance of being produced, and this
     * method takes that into account.
     *
     * @param inv The crusher
     * @return Results of crushing
     */
    public List<ItemStack> getResults(Container inv) {
        int outputUpgrades = inv instanceof MachineBlockEntity
                ? ((MachineBlockEntity<?>) inv).getUpgradeCount(ModMachineUpgrades.OUTPUT_CHANCE.get())
                : 0;
        return results.entrySet().stream()
                .filter(e -> {
                    float chance = e.getValue() + outputUpgrades * Constants.UPGRADE_SECONDARY_OUTPUT_AMOUNT;
                    return MathUtils.tryPercentage(chance);
                })
                .map(e -> e.getKey().copy())
                .toList();
    }

    /**
     * Get the possible results of crushing. Useful for making sure there is enough room in the
     * inventory.
     *
     * @param inv The crusher
     * @return All possible results of crushing
     */
    public Set<ItemStack> getPossibleResults(@SuppressWarnings("unused") Container inv) {
        return results.keySet();
    }

    public List<Pair<ItemStack, Float>> getPossibleResultsWithChances() {
        return results.entrySet().stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue()))
                .toList();
    }

    @Override
    public boolean matches(Container inv, Level dimensionIn) {
        return this.ingredient.test(inv.getItem(0));
    }

    @Deprecated
    @Override
    public ItemStack assemble(Container inv) {
        // DO NOT USE
        return getResultItem();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Deprecated
    @Override
    public ItemStack getResultItem() {
        // DO NOT USE
        return !results.isEmpty() ? results.keySet().iterator().next() : ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CRUSHING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.Types.CRUSHING;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CrushingRecipe> {
        @Override
        public CrushingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            CrushingRecipe recipe = new CrushingRecipe(recipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time", 400);
            recipe.ingredient = Ingredient.fromJson(json.get("ingredient"));
            JsonArray resultsArray = json.getAsJsonArray("results");
            for (JsonElement element : resultsArray) {
                JsonObject obj = element.getAsJsonObject();
                String itemId = GsonHelper.getAsString(obj, "item");
                Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(itemId));
                int count = GsonHelper.getAsInt(obj, "count", 1);
                ItemStack stack = new ItemStack(item, count);
                float chance = GsonHelper.getAsFloat(obj, "chance", 1);
                recipe.results.put(stack, chance);
            }
            return recipe;
        }

        @Override
        public CrushingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            CrushingRecipe recipe = new CrushingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = Ingredient.fromNetwork(buffer);
            int resultCount = buffer.readByte();
            for (int i = 0; i < resultCount; ++i) {
                ResourceLocation itemId = buffer.readResourceLocation();
                int count = buffer.readVarInt();
                float chance = buffer.readFloat();
                Item item = ForgeRegistries.ITEMS.getValue(itemId);
                recipe.results.put(new ItemStack(item, count), chance);
            }
            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CrushingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeByte(recipe.results.size());
            recipe.results.forEach((stack, chance) -> {
                buffer.writeResourceLocation(Objects.requireNonNull(stack.getItem().getRegistryName()));
                buffer.writeVarInt(stack.getCount());
                buffer.writeFloat(chance);
            });
        }
    }
}
