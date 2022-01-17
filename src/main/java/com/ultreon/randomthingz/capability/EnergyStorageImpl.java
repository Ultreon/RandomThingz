package com.ultreon.randomthingz.capability;

import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumMap;

public class EnergyStorageImpl extends EnergyStorageImplBase {
    private final EnumMap<Direction, LazyOptional<Connection>> connections = new EnumMap<>(Direction.class);
    private final BlockEntity tileEntity;

    public EnergyStorageImpl(int capacity, int maxReceive, int maxExtract, BlockEntity tileEntity) {
        super(capacity, maxReceive, maxExtract);
        this.tileEntity = tileEntity;
        Arrays.stream(Direction.values()).forEach(d -> connections.put(d, LazyOptional.of(Connection::new)));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (side == null) return super.getCapability(cap, null);
        return CapabilityEnergy.ENERGY.orEmpty(cap, connections.get(side).cast());
    }

    @Override
    public void invalidate() {
        super.invalidate();
        connections.values().forEach(LazyOptional::invalidate);
    }

    /**
     * Add energy, bypassing max receive limit. Useful for generators, which would normally not
     * receive energy from other blocks.
     *
     * @param amount The amount of energy
     */
    public void createEnergy(int amount) {
        this.energy = Math.min(this.energy + amount, getMaxEnergyStored());
    }

    /**
     * Remove energy, bypassing max extract limit. Useful for machines which consume energy, which
     * would normally not send energy to other blocks.
     *
     * @param amount The amount of energy to remove
     */
    public void consumeEnergy(int amount) {
        this.energy = Math.max(this.energy - amount, 0);
    }

    /**
     * Sets energy directly. Should only be used for syncing data from server to client.
     *
     * @param amount The new amount of stored energy
     */
    public void setEnergyDirectly(int amount) {
        this.energy = amount;
    }

    /**
     * Wrapper which will prevent energy from being sent back to the sender on the same tick
     */
    public class Connection implements IEnergyStorage {
        private long lastReceiveTick;

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            Level dimension = EnergyStorageImpl.this.tileEntity.getLevel();
            if (dimension == null) return 0;

            int received = EnergyStorageImpl.this.receiveEnergy(maxReceive, simulate);
            if (received > 0 && !simulate)
                this.lastReceiveTick = dimension.getGameTime();
            return received;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            Level dimension = EnergyStorageImpl.this.tileEntity.getLevel();
            if (dimension == null) return 0;

            long time = dimension.getGameTime();
            if (time != this.lastReceiveTick) {
                return EnergyStorageImpl.this.extractEnergy(maxExtract, simulate);
            }
            return 0;
        }

        @Override
        public int getEnergyStored() {
            return EnergyStorageImpl.this.getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            return EnergyStorageImpl.this.getMaxEnergyStored();
        }

        @Override
        public boolean canExtract() {
            return EnergyStorageImpl.this.canExtract();
        }

        @Override
        public boolean canReceive() {
            return EnergyStorageImpl.this.canReceive();
        }
    }
}
