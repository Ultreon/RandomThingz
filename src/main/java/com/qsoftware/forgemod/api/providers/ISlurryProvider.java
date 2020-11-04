package com.qsoftware.forgemod.api.providers;

import javax.annotation.Nonnull;
import com.qsoftware.forgemod.api.chemical.slurry.Slurry;
import com.qsoftware.forgemod.api.chemical.slurry.SlurryStack;

public interface ISlurryProvider extends IChemicalProvider<Slurry> {

    @Nonnull
    @Override
    default SlurryStack getStack(long size) {
        return new SlurryStack(getChemical(), size);
    }
}