package com.ultreon.randomthingz.util;

import com.ultreon.randomthingz.block.machines.IEnergyHandler;
import lombok.experimental.UtilityClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public final class EnergyUtils {
    public static void trySendToNeighbors(BlockGetter dimension, BlockPos pos, IEnergyHandler energyHandler, int maxSend) {
        for (Direction side : Direction.values()) {
            if (energyHandler.getEnergyStored() == 0) {
                return;
            }
            trySendTo(dimension, pos, energyHandler, maxSend, side);
        }
    }

    public static void trySendTo(BlockGetter dimension, BlockPos pos, IEnergyHandler energyHandler, int maxSend, Direction side) {
        BlockEntity tileEntity = dimension.getBlockEntity(pos.relative(side));
        if (tileEntity != null) {
            IEnergyStorage energy = energyHandler.getEnergy(side).orElse(new EnergyStorage(0));
            tileEntity.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).ifPresent(other -> trySendEnergy(maxSend, energy, other));
        }
    }

    private static void trySendEnergy(int maxSend, IEnergyStorage energy, IEnergyStorage other) {
        if (other.canReceive()) {
            int toSend = energy.extractEnergy(maxSend, true);
            int sent = other.receiveEnergy(toSend, false);
            if (sent > 0) {
                energy.extractEnergy(sent, false);
            }
        }
    }

    /**
     * Gets the energy capability for the block at the given position. If it does not have an energy
     * capability, or the block is not a tile entity, this returns null.
     *
     * @param dimension The dimension
     * @param pos       The position to check
     * @return The energy capability, or null if not present
     */
    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static IEnergyStorage getEnergy(LevelReader dimension, BlockPos pos) {
        if (!dimension.isAreaLoaded(pos, 1)) return null;
        BlockEntity tileEntity = dimension.getBlockEntity(pos);
        return tileEntity != null ? tileEntity.getCapability(CapabilityEnergy.ENERGY).orElse(null) : null;
    }

    /**
     * Gets the energy capability of the object (item, etc), or null if it does not have one.
     *
     * @param provider The capability provider
     * @return The energy capability, or null if not present
     */
    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static IEnergyStorage getEnergy(ICapabilityProvider provider) {
        return provider.getCapability(CapabilityEnergy.ENERGY).orElse(null);
    }

    /**
     * Gets the energy capability of the object (item, etc), or null if it does not have one. Tries
     * to get the capability for the given side first, then null side.
     *
     * @param provider The capability provider
     * @param side     The side being accessed
     * @return The energy capability, or null if not present
     */
    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static IEnergyStorage getEnergyFromSideOrNull(ICapabilityProvider provider, Direction side) {
        return provider.getCapability(CapabilityEnergy.ENERGY, side).orElse(provider.getCapability(CapabilityEnergy.ENERGY).orElse(null));
    }
}
