package com.qsoftware.forgemod.api.chemical.pigment;

import com.qsoftware.forgemod.api.chemical.IChemicalHandler;
import com.qsoftware.forgemod.api.chemical.IMekanismChemicalHandler;
import com.qsoftware.forgemod.api.chemical.ISidedChemicalHandler;

public interface IPigmentHandler extends IChemicalHandler<Pigment, PigmentStack>, IEmptyPigmentProvider {

    /**
     * A sided variant of {@link IPigmentHandler}
     */
    interface ISidedPigmentHandler extends ISidedChemicalHandler<Pigment, PigmentStack>, IPigmentHandler {
    }

    interface IMekanismPigmentHandler extends IMekanismChemicalHandler<Pigment, PigmentStack, IPigmentTank>, ISidedPigmentHandler {
    }
}