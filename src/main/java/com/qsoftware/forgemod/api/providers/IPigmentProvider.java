package com.qsoftware.forgemod.api.providers;

import javax.annotation.Nonnull;
import com.qsoftware.forgemod.api.chemical.pigment.Pigment;
import com.qsoftware.forgemod.api.chemical.pigment.PigmentStack;

public interface IPigmentProvider extends IChemicalProvider<Pigment> {

    @Nonnull
    @Override
    default PigmentStack getStack(long size) {
        return new PigmentStack(getChemical(), size);
    }
}