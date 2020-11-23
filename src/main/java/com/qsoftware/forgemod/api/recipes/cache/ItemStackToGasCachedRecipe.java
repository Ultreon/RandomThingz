package com.qsoftware.forgemod.api.recipes.cache;

import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.NonNull;
import com.qsoftware.forgemod.api.chemical.gas.Gas;
import com.qsoftware.forgemod.api.chemical.gas.GasStack;
import com.qsoftware.forgemod.api.recipes.ItemStackToGasRecipe;
import com.qsoftware.forgemod.api.recipes.cache.chemical.ItemStackToChemicalCachedRecipe;
import com.qsoftware.forgemod.api.recipes.inputs.IInputHandler;
import com.qsoftware.forgemod.api.recipes.outputs.IOutputHandler;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemStackToGasCachedRecipe extends ItemStackToChemicalCachedRecipe<Gas, GasStack, ItemStackToGasRecipe> {

    public ItemStackToGasCachedRecipe(ItemStackToGasRecipe recipe, IInputHandler<@NonNull ItemStack> inputHandler, IOutputHandler<@NonNull GasStack> outputHandler) {
        super(recipe, inputHandler, outputHandler);
    }
}