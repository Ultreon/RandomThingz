package com.qsoftware.forgemod.api.chemical.gas;

import com.qsoftware.forgemod.api.NBTConstants;
import com.qsoftware.forgemod.api.chemical.IChemicalTank;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants.NBT;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Convenience extension to make working with generics easier.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface IGasTank extends IChemicalTank<Gas, GasStack>, IEmptyGasProvider {

    @Override
    default GasStack createStack(GasStack stored, long size) {
        return new GasStack(stored, size);
    }

    @Override
    default void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains(NBTConstants.STORED, NBT.TAG_COMPOUND)) {
            setStackUnchecked(GasStack.readFromNBT(nbt.getCompound(NBTConstants.STORED)));
        }
    }
}