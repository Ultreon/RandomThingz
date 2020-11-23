package com.qsoftware.forgemod.api.providers;

import com.qsoftware.forgemod.api.chemical.gas.Gas;
import com.qsoftware.forgemod.api.chemical.gas.GasStack;

import javax.annotation.Nonnull;

public interface IGasProvider extends IChemicalProvider<Gas> {

    @Nonnull
    @Override
    default GasStack getStack(long size) {
        return new GasStack(getChemical(), size);
    }
}