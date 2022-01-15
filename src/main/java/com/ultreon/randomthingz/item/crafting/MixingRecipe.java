package com.ultreon.randomthingz.item.crafting;

import com.google.gson.JsonObject;
import com.qsoftware.modlib.api.crafting.recipe.fluid.FluidIngredient;
import com.qsoftware.modlib.api.crafting.recipe.fluid.IFluidInventory;
import com.qsoftware.modlib.api.crafting.recipe.fluid.IFluidRecipe;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class MixingRecipe implements IFluidRecipe<IFluidInventory> {
    private final ResourceLocation recipeId;
    private final List<FluidIngredient> ingredients = NonNullList.create();
    @Getter
    private int processTime;
    @Getter
    private FluidStack result;

    @Override
    public boolean matches(IFluidInventory inv, Level dimensionIn) {
        final int inputTanks = Math.min(4, inv.getTanks());
        Set<Integer> matchedTanks = new HashSet<>();

        for (FluidIngredient ingredient : ingredients) {
            for (int i = 0; i < inputTanks; ++i) {
                FluidStack stack = inv.getFluidInTank(i);

                if (!matchedTanks.contains(i) && ingredient.test(stack)) {
                    matchedTanks.add(i);
                    break;
                }
            }
        }

        return matchedTanks.size() == ingredients.size();
    }

    @Override
    public List<FluidStack> getFluidResults(IFluidInventory inv) {
        return getFluidOutputs();
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return Collections.singletonList(result.copy());
    }

    @Override
    public List<FluidIngredient> getFluidIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MIXING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.Types.MIXING;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MixingRecipe> {
        @Override
        public MixingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            MixingRecipe recipe = new MixingRecipe(recipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time");
            GsonHelper.getAsJsonArray(json, "ingredients").forEach(e ->
                    recipe.ingredients.add(FluidIngredient.deserialize(e.getAsJsonObject())));
            recipe.result = IFluidRecipe.deserializeFluid(GsonHelper.getAsJsonObject(json, "result"));
            return recipe;
        }

        @Nullable
        @Override
        public MixingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            MixingRecipe recipe = new MixingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            int ingredientCount = buffer.readByte();
            for (int i = 0; i < ingredientCount; ++i) {
                recipe.ingredients.add(FluidIngredient.read(buffer));
            }
            recipe.result = IFluidRecipe.readFluid(buffer);
            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MixingRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            buffer.writeByte(recipe.ingredients.size());
            recipe.ingredients.forEach(ingredient -> ingredient.write(buffer));
            IFluidRecipe.writeFluid(buffer, recipe.result);
        }
    }
}
