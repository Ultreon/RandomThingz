package com.qsoftware.forgemod.api.providers;

import javax.annotation.Nonnull;
import com.qsoftware.forgemod.api.chemical.gas.Gas;
import com.qsoftware.forgemod.api.chemical.gas.GasStack;

public interface IGasProvider extends IChemicalProvider<Gas> {

    @Nonnull
    @Override
    default GasStack getStack(long size) {
        return new GasStack(getChemical(), size);
    }
}