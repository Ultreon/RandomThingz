package com.qsoftware.forgemod.api.chemical.merged;

import com.qsoftware.forgemod.api.MekanismAPI;
import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.chemical.Chemical;
import com.qsoftware.forgemod.api.chemical.ChemicalType;
import com.qsoftware.forgemod.api.chemical.gas.Gas;
import com.qsoftware.forgemod.api.chemical.infuse.InfuseType;
import com.qsoftware.forgemod.api.chemical.pigment.Pigment;
import com.qsoftware.forgemod.api.chemical.slurry.Slurry;
import com.qsoftware.forgemod.api.text.IHasTextComponent;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BoxedChemical implements IHasTextComponent {

    //TODO: Make a subclass for the empty implementation?
    public static final BoxedChemical EMPTY = new BoxedChemical(ChemicalType.GAS, MekanismAPI.EMPTY_GAS);
    private final ChemicalType chemicalType;
    private final Chemical<?> chemical;

    protected BoxedChemical(ChemicalType chemicalType, Chemical<?> chemical) {
        this.chemicalType = chemicalType;
        this.chemical = chemical;
    }

    @SuppressWarnings("RedundantCast")
    public static BoxedChemical read(PacketBuffer buffer) {
        //Note: Casts are needed for compiling so it knows how to read it properly
        ChemicalType chemicalType = buffer.readEnumValue(ChemicalType.class);
        if (chemicalType == ChemicalType.GAS) {
            return new BoxedChemical(chemicalType, (Gas) buffer.readRegistryId());
        } else if (chemicalType == ChemicalType.INFUSION) {
            return new BoxedChemical(chemicalType, (InfuseType) buffer.readRegistryId());
        } else if (chemicalType == ChemicalType.PIGMENT) {
            return new BoxedChemical(chemicalType, (Pigment) buffer.readRegistryId());
        } else if (chemicalType == ChemicalType.SLURRY) {
            return new BoxedChemical(chemicalType, (Slurry) buffer.readRegistryId());
        } else {
            throw new IllegalStateException("Unknown chemical type");
        }
    }

    public static BoxedChemical read(@Nullable CompoundNBT nbt) {
        ChemicalType chemicalType = ChemicalType.fromNBT(nbt);
        Chemical<?> chemical = null;
        if (chemicalType == ChemicalType.GAS) {
            chemical = Gas.readFromNBT(nbt);
        } else if (chemicalType == ChemicalType.INFUSION) {
            chemical = InfuseType.readFromNBT(nbt);
        } else if (chemicalType == ChemicalType.PIGMENT) {
            chemical = Pigment.readFromNBT(nbt);
        } else if (chemicalType == ChemicalType.SLURRY) {
            chemical = Slurry.readFromNBT(nbt);
        }
        return chemicalType == null || chemical == null ? EMPTY : new BoxedChemical(chemicalType, chemical);
    }

    public static BoxedChemical box(Chemical<?> chemical) {
        return new BoxedChemical(ChemicalType.getTypeFor(chemical), chemical);
    }

    public boolean isEmpty() {
        return this == EMPTY || chemical.isEmptyType();
    }

    public ChemicalType getChemicalType() {
        return chemicalType;
    }

    public CompoundNBT write(CompoundNBT nbt) {
        chemicalType.write(nbt);
        chemical.write(nbt);
        return nbt;
    }

    public void write(PacketBuffer buffer) {
        buffer.writeEnumValue(chemicalType);
        if (chemicalType == ChemicalType.GAS) {
            buffer.writeRegistryId((Gas) chemical);
        } else if (chemicalType == ChemicalType.INFUSION) {
            buffer.writeRegistryId((InfuseType) chemical);
        } else if (chemicalType == ChemicalType.PIGMENT) {
            buffer.writeRegistryId((Pigment) chemical);
        } else if (chemicalType == ChemicalType.SLURRY) {
            buffer.writeRegistryId((Slurry) chemical);
        } else {
            throw new IllegalStateException("Unknown chemical type");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoxedChemical other = (BoxedChemical) o;
        return chemicalType == other.chemicalType && chemical == other.chemical;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chemicalType, chemical);
    }

    public Chemical<?> getChemical() {
        return chemical;
    }

    @Override
    public ITextComponent getTextComponent() {
        return chemical.getTextComponent();
    }
}