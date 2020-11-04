package com.qsoftware.forgemod.api.recipes.inputs.chemical;

import javax.annotation.Nonnull;
import com.qsoftware.forgemod.api.annotations.NonNull;
import com.qsoftware.forgemod.api.chemical.Chemical;
import com.qsoftware.forgemod.api.chemical.ChemicalStack;
import com.qsoftware.forgemod.api.recipes.inputs.InputIngredient;

public interface IChemicalStackIngredient<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> extends InputIngredient<@NonNull STACK> {

    boolean testType(@Nonnull CHEMICAL chemical);

    /**
     * @apiNote This is for use in implementations and should probably not be accessed for other purposes
     */
    ChemicalIngredientInfo<CHEMICAL, STACK> getIngredientInfo();
}