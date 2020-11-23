package com.qsoftware.forgemod.api.chemical.gas;

import com.qsoftware.forgemod.api.chemical.IEmptyStackProvider;

import javax.annotation.Nonnull;

public interface IEmptyGasProvider extends IEmptyStackProvider<Gas, GasStack> {

    @Nonnull
    @Override
    default GasStack getEmptyStack() {
        return GasStack.EMPTY;
    }
}