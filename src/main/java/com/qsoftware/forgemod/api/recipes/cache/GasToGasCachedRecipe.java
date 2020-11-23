package com.qsoftware.forgemod.api.recipes.cache;

import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.NonNull;
import com.qsoftware.forgemod.api.chemical.gas.Gas;
import com.qsoftware.forgemod.api.chemical.gas.GasStack;
import com.qsoftware.forgemod.api.recipes.GasToGasRecipe;
import com.qsoftware.forgemod.api.recipes.cache.chemical.ChemicalToChemicalCachedRecipe;
import com.qsoftware.forgemod.api.recipes.inputs.IInputHandler;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.GasStackIngredient;
import com.qsoftware.forgemod.api.recipes.outputs.IOutputHandler;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
public class GasToGasCachedRecipe extends ChemicalToChemicalCachedRecipe<Gas, GasStack, GasStackIngredient, GasToGasRecipe> {

    public GasToGasCachedRecipe(GasToGasRecipe recipe, IInputHandler<@NonNull GasStack> inputHandler, IOutputHandler<@NonNull GasStack> outputHandler) {
        super(recipe, inputHandler, outputHandler);
    }
}