package com.qsoftware.forgemod.api.chemical;

import com.qsoftware.forgemod.api.NBTConstants;
import com.qsoftware.forgemod.api.chemical.gas.Gas;
import com.qsoftware.forgemod.api.chemical.infuse.InfuseType;
import com.qsoftware.forgemod.api.chemical.pigment.Pigment;
import com.qsoftware.forgemod.api.chemical.slurry.Slurry;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.util.Constants.NBT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Predicate;

//TODO: Make the chemicals know their own chemical type
public enum ChemicalType implements IStringSerializable {
    GAS("gas", c -> c instanceof Gas),
    INFUSION("infuse_type", c -> c instanceof InfuseType),
    PIGMENT("pigment", c -> c instanceof Pigment),
    SLURRY("slurry", c -> c instanceof Slurry);

    private static final Map<String, ChemicalType> nameToType = new Object2ObjectOpenHashMap<>();

    static {
        for (ChemicalType type : values()) {
            nameToType.put(type.getString(), type);
        }
    }

    private final Predicate<Chemical<?>> instanceCheck;
    private final String name;

    ChemicalType(String name, Predicate<Chemical<?>> instanceCheck) {
        this.name = name;
        this.instanceCheck = instanceCheck;
    }

    @Nullable
    public static ChemicalType fromString(String name) {
        return nameToType.get(name);
    }

    @Nullable
    public static ChemicalType fromNBT(@Nullable CompoundNBT nbt) {
        if (nbt != null && nbt.contains(NBTConstants.CHEMICAL_TYPE, NBT.TAG_STRING)) {
            return fromString(nbt.getString(NBTConstants.CHEMICAL_TYPE));
        }
        return null;
    }

    public static ChemicalType getTypeFor(Chemical<?> chemical) {
        if (chemical instanceof Gas) {
            return GAS;
        } else if (chemical instanceof InfuseType) {
            return INFUSION;
        } else if (chemical instanceof Pigment) {
            return PIGMENT;
        } else if (chemical instanceof Slurry) {
            return SLURRY;
        }
        throw new IllegalStateException("Unknown chemical type");
    }

    public static ChemicalType getTypeFor(ChemicalStack<?> stack) {
        return getTypeFor(stack.getType());
    }

    public static ChemicalType getTypeFor(IChemicalStackIngredient<?, ?> ingredient) {
        if (ingredient instanceof GasStackIngredient) {
            return GAS;
        } else if (ingredient instanceof InfusionStackIngredient) {
            return INFUSION;
        } else if (ingredient instanceof PigmentStackIngredient) {
            return PIGMENT;
        } else if (ingredient instanceof SlurryStackIngredient) {
            return SLURRY;
        }
        throw new IllegalStateException("Unknown chemical ingredient type");
    }

    @Nonnull
    @Override
    public String getString() {
        return name;
    }

    public boolean isInstance(Chemical<?> chemical) {
        return instanceCheck.test(chemical);
    }

    public void write(@Nonnull CompoundNBT nbt) {
        nbt.putString(NBTConstants.CHEMICAL_TYPE, getString());
    }
}