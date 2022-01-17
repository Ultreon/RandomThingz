package com.ultreon.randomthingz.block.machines;

import com.ultreon.randomthingz.capability.EnergyStorageImpl;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public interface IEnergyHandler {
    EnergyStorageImpl getEnergyImpl();

    default LazyOptional<IEnergyStorage> getEnergy(@Nullable Direction side) {
        return getEnergyImpl().getCapability(CapabilityEnergy.ENERGY, side);
    }

    default int getEnergyStored() {
        IEnergyStorage energy = getEnergy(null).orElse(new EnergyStorage(100_000));
        return energy.getEnergyStored();
    }

    default int getMaxEnergyStored() {
        IEnergyStorage energy = getEnergy(null).orElse(new EnergyStorage(100_000));
        return energy.getMaxEnergyStored();
    }

    default void setEnergyStoredDirectly(int value) {
        getEnergy(null).ifPresent(e -> {
            if (e instanceof EnergyStorageImpl) {
                ((EnergyStorageImpl) e).setEnergyDirectly(value);
            }
        });
    }

    default void readEnergy(CompoundTag tags) {
        setEnergyStoredDirectly(tags.getInt("Energy"));
    }

    default void writeEnergy(CompoundTag tags) {
        tags.putInt("Energy", getEnergyStored());
    }
}
