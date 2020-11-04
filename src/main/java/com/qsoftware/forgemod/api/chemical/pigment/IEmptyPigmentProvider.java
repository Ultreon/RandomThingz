package com.qsoftware.forgemod.api.chemical.pigment;

import javax.annotation.Nonnull;
import com.qsoftware.forgemod.api.chemical.IEmptyStackProvider;

public interface IEmptyPigmentProvider extends IEmptyStackProvider<Pigment, PigmentStack> {

    @Nonnull
    @Override
    default PigmentStack getEmptyStack() {
        return PigmentStack.EMPTY;
    }
}