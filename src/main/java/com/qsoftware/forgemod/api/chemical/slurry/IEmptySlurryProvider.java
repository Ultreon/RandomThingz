package com.qsoftware.forgemod.api.chemical.slurry;

import com.qsoftware.forgemod.api.chemical.IEmptyStackProvider;

import javax.annotation.Nonnull;

public interface IEmptySlurryProvider extends IEmptyStackProvider<Slurry, SlurryStack> {

    @Nonnull
    @Override
    default SlurryStack getEmptyStack() {
        return SlurryStack.EMPTY;
    }
}