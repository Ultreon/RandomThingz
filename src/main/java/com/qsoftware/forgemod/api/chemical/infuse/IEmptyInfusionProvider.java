package com.qsoftware.forgemod.api.chemical.infuse;

import com.qsoftware.forgemod.api.chemical.IEmptyStackProvider;

import javax.annotation.Nonnull;

public interface IEmptyInfusionProvider extends IEmptyStackProvider<InfuseType, InfusionStack> {

    @Nonnull
    @Override
    default InfusionStack getEmptyStack() {
        return InfusionStack.EMPTY;
    }
}