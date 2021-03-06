package com.ultreon.randomthingz.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ultreon.modlib.api.crafting.recipe.fluid.FluidInventory;
import com.ultreon.modlib.api.crafting.recipe.fluid.FluidRecipe;
import com.ultreon.modlib.api.crafting.recipe.fluid.FluidIngredient;
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
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class RefiningRecipe implements FluidRecipe<FluidInventory> {
    private final ResourceLocation recipeId;
    private final List<FluidStack> outputs = NonNullList.create();
    @Getter
    private int processTime;
    @Getter
    private FluidIngredient ingredient;

    @Override
    public boolean matches(FluidInventory inv, Level dimensionIn) {
        return ingredient.test(inv.getFluidInTank(0));
    }

    @Override
    public List<FluidStack> getFluidResults(FluidInventory inv) {
        return getFluidOutputs();
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        List<FluidStack> results = NonNullList.create();
        outputs.forEach(s -> results.add(s.copy()));
        return results;
    }

    @Override
    public List<FluidIngredient> getFluidIngredients() {
        return Collections.singletonList(ingredient);
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.REFINING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.Types.REFINING;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RefiningRecipe> {
        @Override
        public RefiningRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            RefiningRecipe recipe = new RefiningRecipe(recipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time");
            recipe.ingredient = FluidIngredient.deserialize(json.getAsJsonObject("ingredient"));
            for (JsonElement je : GsonHelper.getAsJsonArray(json, "results")) {
                FluidStack stack = FluidRecipe.deserializeFluid(je.getAsJsonObject());
                if (!stack.isEmpty()) {
                    recipe.outputs.add(stack);
                }
            }
            return recipe;
        }

        @Nullable
        @Override
        public RefiningRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            RefiningRecipe recipe = new RefiningRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = FluidIngredient.read(buffer);
            int count = buffer.readByte();
            for (int i = 0; i < count; ++i) {
                FluidStack stack = FluidRecipe.readFluid(buffer);
                if (!stack.isEmpty()) {
                    recipe.outputs.add(stack);
                }
            }
            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RefiningRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.write(buffer);
            buffer.writeByte(recipe.outputs.size());
            recipe.outputs.forEach(s -> FluidRecipe.writeFluid(buffer, s));
        }
    }
}
