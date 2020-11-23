package com.qsoftware.forgemod.api.chemical.merged;

import com.qsoftware.forgemod.api.chemical.ChemicalStack;
import com.qsoftware.forgemod.api.chemical.ChemicalType;
import com.qsoftware.forgemod.api.chemical.gas.GasStack;
import com.qsoftware.forgemod.api.chemical.infuse.InfusionStack;
import com.qsoftware.forgemod.api.chemical.pigment.PigmentStack;
import com.qsoftware.forgemod.api.chemical.slurry.SlurryStack;
import com.qsoftware.forgemod.api.text.IHasTextComponent;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class BoxedChemicalStack implements IHasTextComponent {

    //TODO: Make a subclass for the empty implementation?
    public static final BoxedChemicalStack EMPTY = new BoxedChemicalStack(ChemicalType.GAS, GasStack.EMPTY);
    private final ChemicalType chemicalType;
    private final ChemicalStack<?> chemicalStack;

    private BoxedChemicalStack(ChemicalType chemicalType, ChemicalStack<?> chemicalStack) {
        this.chemicalType = chemicalType;
        this.chemicalStack = chemicalStack;
    }

    public static BoxedChemicalStack box(ChemicalStack<?> chemicalStack) {
        return new BoxedChemicalStack(ChemicalType.getTypeFor(chemicalStack), chemicalStack);
    }

    public static BoxedChemicalStack read(@Nullable CompoundNBT nbt) {
        ChemicalType chemicalType = ChemicalType.fromNBT(nbt);
        ChemicalStack<?> stack = null;
        if (chemicalType == ChemicalType.GAS) {
            stack = GasStack.readFromNBT(nbt);
        } else if (chemicalType == ChemicalType.INFUSION) {
            stack = InfusionStack.readFromNBT(nbt);
        } else if (chemicalType == ChemicalType.PIGMENT) {
            stack = PigmentStack.readFromNBT(nbt);
        } else if (chemicalType == ChemicalType.SLURRY) {
            stack = SlurryStack.readFromNBT(nbt);
        }
        return chemicalType == null || stack == null ? EMPTY : new BoxedChemicalStack(chemicalType, stack);
    }

    public BoxedChemical getType() {
        return new BoxedChemical(chemicalType, chemicalStack.getType());
    }

    public ChemicalType getChemicalType() {
        return chemicalType;
    }

    public boolean isEmpty() {
        return this == EMPTY || chemicalStack.isEmpty();
    }

    public CompoundNBT write(CompoundNBT nbt) {
        chemicalType.write(nbt);
        chemicalStack.write(nbt);
        return nbt;
    }

    public ChemicalStack<?> getChemicalStack() {
        return chemicalStack;
    }

    @Override
    public ITextComponent getTextComponent() {
        return chemicalStack.getTextComponent();
    }
}