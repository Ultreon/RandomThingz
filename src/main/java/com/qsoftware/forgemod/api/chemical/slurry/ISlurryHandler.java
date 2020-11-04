package com.qsoftware.forgemod.api.chemical.slurry;

import com.qsoftware.forgemod.api.chemical.IChemicalHandler;
import com.qsoftware.forgemod.api.chemical.IMekanismChemicalHandler;
import com.qsoftware.forgemod.api.chemical.ISidedChemicalHandler;

public interface ISlurryHandler extends IChemicalHandler<Slurry, SlurryStack>, IEmptySlurryProvider {

    /**
     * A sided variant of {@link ISlurryHandler}
     */
    interface ISidedSlurryHandler extends ISidedChemicalHandler<Slurry, SlurryStack>, ISlurryHandler {
    }

    interface IMekanismSlurryHandler extends IMekanismChemicalHandler<Slurry, SlurryStack, ISlurryTank>, ISidedSlurryHandler {
    }
}