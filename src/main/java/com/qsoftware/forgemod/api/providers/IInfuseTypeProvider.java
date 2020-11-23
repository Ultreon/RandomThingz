package com.qsoftware.forgemod.api.providers;

import com.qsoftware.forgemod.api.chemical.infuse.InfuseType;
import com.qsoftware.forgemod.api.chemical.infuse.InfusionStack;

import javax.annotation.Nonnull;

public interface IInfuseTypeProvider extends IChemicalProvider<InfuseType> {

    @Nonnull
    @Override
    default InfusionStack getStack(long size) {
        return new InfusionStack(getChemical(), size);
    }
}