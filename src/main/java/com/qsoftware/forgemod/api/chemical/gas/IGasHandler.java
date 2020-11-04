package com.qsoftware.forgemod.api.chemical.gas;

import com.qsoftware.forgemod.api.chemical.IChemicalHandler;
import com.qsoftware.forgemod.api.chemical.IMekanismChemicalHandler;
import com.qsoftware.forgemod.api.chemical.ISidedChemicalHandler;

public interface IGasHandler extends IChemicalHandler<Gas, GasStack>, IEmptyGasProvider {

    /**
     * A sided variant of {@link IGasHandler}
     */
    interface ISidedGasHandler extends ISidedChemicalHandler<Gas, GasStack>, IGasHandler {
    }

    interface IMekanismGasHandler extends IMekanismChemicalHandler<Gas, GasStack, IGasTank>, ISidedGasHandler {
    }
}