package com.qsoftware.forgemod.api.chemical.infuse;

import com.qsoftware.forgemod.api.chemical.IChemicalHandler;
import com.qsoftware.forgemod.api.chemical.IMekanismChemicalHandler;
import com.qsoftware.forgemod.api.chemical.ISidedChemicalHandler;

public interface IInfusionHandler extends IChemicalHandler<InfuseType, InfusionStack>, IEmptyInfusionProvider {

    /**
     * A sided variant of {@link IInfusionHandler}
     */
    interface ISidedInfusionHandler extends ISidedChemicalHandler<InfuseType, InfusionStack>, IInfusionHandler {
    }

    interface IMekanismInfusionHandler extends IMekanismChemicalHandler<InfuseType, InfusionStack, IInfusionTank>, ISidedInfusionHandler {
    }
}