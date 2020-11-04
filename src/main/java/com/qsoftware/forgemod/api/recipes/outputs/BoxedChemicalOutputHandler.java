package com.qsoftware.forgemod.api.recipes.outputs;

import java.util.Objects;
import javax.annotation.ParametersAreNonnullByDefault;
import com.qsoftware.forgemod.api.chemical.ChemicalStack;
import com.qsoftware.forgemod.api.chemical.IChemicalTank;
import com.qsoftware.forgemod.api.chemical.merged.BoxedChemicalStack;
import com.qsoftware.forgemod.api.chemical.merged.MergedChemicalTank;

@ParametersAreNonnullByDefault
public class BoxedChemicalOutputHandler {

    private final MergedChemicalTank chemicalTank;

    public BoxedChemicalOutputHandler(MergedChemicalTank chemicalTank) {
        this.chemicalTank = Objects.requireNonNull(chemicalTank);
    }

    public void handleOutput(BoxedChemicalStack toOutput, int operations) {
        handleOutput(chemicalTank.getTankForType(toOutput.getChemicalType()), toOutput.getChemicalStack(), operations);
    }

    private <STACK extends ChemicalStack<?>> void handleOutput(IChemicalTank<?, ?> tank, STACK stack, int operations) {
        OutputHelper.handleOutput((IChemicalTank<?, STACK>) tank, stack, operations);
    }

    public int operationsRoomFor(BoxedChemicalStack toOutput, int currentMax) {
        return operationsRoomFor(chemicalTank.getTankForType(toOutput.getChemicalType()), toOutput.getChemicalStack(), currentMax);
    }

    private <STACK extends ChemicalStack<?>> int operationsRoomFor(IChemicalTank<?, ?> tank, STACK stack, int currentMax) {
        return OutputHelper.operationsRoomFor((IChemicalTank<?, STACK>) tank, stack, currentMax);
    }
}