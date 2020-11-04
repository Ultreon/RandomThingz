package com.qsoftware.forgemod.api.chemical.infuse;

import javax.annotation.Nonnull;
import com.qsoftware.forgemod.api.chemical.IEmptyStackProvider;

public interface IEmptyInfusionProvider extends IEmptyStackProvider<InfuseType, InfusionStack> {

    @Nonnull
    @Override
    default InfusionStack getEmptyStack() {
        return InfusionStack.EMPTY;
    }
}