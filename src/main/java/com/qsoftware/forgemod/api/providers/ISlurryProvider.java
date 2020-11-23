package com.qsoftware.forgemod.api.providers;

import com.qsoftware.forgemod.api.chemical.slurry.Slurry;
import com.qsoftware.forgemod.api.chemical.slurry.SlurryStack;

import javax.annotation.Nonnull;

public interface ISlurryProvider extends IChemicalProvider<Slurry> {

    @Nonnull
    @Override
    default SlurryStack getStack(long size) {
        return new SlurryStack(getChemical(), size);
    }
}