package com.qsoftware.forgemod.api.chemical.slurry;

import javax.annotation.Nonnull;
import com.qsoftware.forgemod.api.chemical.IEmptyStackProvider;

public interface IEmptySlurryProvider extends IEmptyStackProvider<Slurry, SlurryStack> {

    @Nonnull
    @Override
    default SlurryStack getEmptyStack() {
        return SlurryStack.EMPTY;
    }
}