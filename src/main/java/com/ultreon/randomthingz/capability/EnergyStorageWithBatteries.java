package com.ultreon.randomthingz.capability;

import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnergyStorageWithBatteries<T extends BlockEntity & Container> extends EnergyStorageImpl {
    private final Container container;
    private final LazyOptional<EnergyStorageWithBatteries<?>> lazy;

    protected int energyInternal;
    protected int capacityInternal;
    protected int maxReceive;
    protected int maxExtract;

    public EnergyStorageWithBatteries(T tileEntity, int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract, tileEntity);
        this.container = tileEntity;
        this.capacityInternal = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.lazy = LazyOptional.of(() -> this);
    }

    @SuppressWarnings("TypeMayBeWeakened")
    private static boolean isBattery(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY).isPresent();
    }

    public int getInternalEnergyStored() {
        return energyInternal;
    }

    private int getBatteryCount() {
        int count = 0;
        for (int i = 0; i < container.getContainerSize(); ++i) {
            if (isBattery(container.getItem(i))) {
                ++count;
            }
        }
        return count;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive()) return 0;

        int batteryCount = getBatteryCount();
        int left = maxReceive;
        if (batteryCount > 0) {
            int perBattery = left / batteryCount;

            for (int i = 0; i < container.getContainerSize(); ++i) {
                ItemStack stack = container.getItem(i);
                LazyOptional<IEnergyStorage> optional = stack.getCapability(CapabilityEnergy.ENERGY);

                if (optional.isPresent()) {
                    left -= optional.orElseThrow(IllegalStateException::new).receiveEnergy(perBattery, simulate);
                }
            }
        }

        int internalReceive = Math.min(capacityInternal - energyInternal, Math.min(this.maxReceive, left));
        if (!simulate)
            energyInternal += internalReceive;
        return maxReceive - (left - internalReceive);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract()) return 0;

        int internalExtract = Math.min(energyInternal, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energyInternal -= internalExtract;
        if (internalExtract >= maxExtract)
            return internalExtract;

        int batteryCount = getBatteryCount();
        int extracted = internalExtract;
        if (batteryCount > 0) {
            int perBattery = (maxExtract - internalExtract) / batteryCount;

            for (int i = 0; i < container.getContainerSize(); ++i) {
                ItemStack stack = container.getItem(i);
                LazyOptional<IEnergyStorage> optional = stack.getCapability(CapabilityEnergy.ENERGY);

                if (optional.isPresent()) {
                    extracted += optional.orElseThrow(IllegalStateException::new).extractEnergy(perBattery, simulate);
                }
            }
        }

        return extracted;
    }

    public int extractInternalEnergy(int maxExtract, boolean simulate) {
        if (!canExtract()) return 0;

        int internalExtract = Math.min(energyInternal, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energyInternal -= internalExtract;
        return internalExtract;
    }

    @Override
    public int getEnergyStored() {
        int ret = energyInternal;
        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            LazyOptional<IEnergyStorage> optional = stack.getCapability(CapabilityEnergy.ENERGY);

            if (optional.isPresent()) {
                ret += optional.orElseThrow(IllegalStateException::new).getEnergyStored();
            }
        }
        return ret;
    }

    @Override
    public int getMaxEnergyStored() {
        int ret = capacityInternal;
        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            LazyOptional<IEnergyStorage> optional = stack.getCapability(CapabilityEnergy.ENERGY);

            if (optional.isPresent()) {
                ret += optional.orElseThrow(IllegalStateException::new).getMaxEnergyStored();
            }
        }
        return ret;
    }

    @Override
    public void setEnergyDirectly(int amount) {
        this.energyInternal = amount;
    }

    @Override
    public boolean canExtract() {
        return maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return maxReceive > 0;
    }

    @NotNull
    @Override
    public <S> LazyOptional<S> getCapability(@NotNull Capability<S> cap, @Nullable Direction side) {
        return CapabilityEnergy.ENERGY.orEmpty(cap, lazy.cast());
    }
}
