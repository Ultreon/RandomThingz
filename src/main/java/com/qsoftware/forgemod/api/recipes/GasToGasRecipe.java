package com.qsoftware.forgemod.api.recipes;

import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.chemical.gas.Gas;
import com.qsoftware.forgemod.api.chemical.gas.GasStack;
import com.qsoftware.forgemod.api.recipes.chemical.ChemicalToChemicalRecipe;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.GasStackIngredient;
import net.minecraft.util.ResourceLocation;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class GasToGasRecipe extends ChemicalToChemicalRecipe<Gas, GasStack, GasStackIngredient> {

    public GasToGasRecipe(ResourceLocation id, GasStackIngredient input, GasStack output) {
        super(id, input, output);
    }

    @Override
    public GasStack getOutput(GasStack input) {
        return output.copy();
    }
}