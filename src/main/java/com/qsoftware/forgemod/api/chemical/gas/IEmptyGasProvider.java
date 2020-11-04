package com.qsoftware.forgemod.api.chemical.gas;

import javax.annotation.Nonnull;
import com.qsoftware.forgemod.api.chemical.IEmptyStackProvider;

public interface IEmptyGasProvider extends IEmptyStackProvider<Gas, GasStack> {

    @Nonnull
    @Override
    default GasStack getEmptyStack() {
        return GasStack.EMPTY;
    }
}