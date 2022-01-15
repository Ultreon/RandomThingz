package com.ultreon.randomthingz.block.machines;

import com.qsoftware.modlib.silentlib.tile.LockableSidedInventoryTileEntity;
import com.qsoftware.modlib.silentlib.tile.SyncVariable;
import com.ultreon.randomthingz.capability.EnergyStorageImpl;
import com.ultreon.randomthingz.util.EnergyUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractEnergyInventoryTileEntity extends LockableSidedInventoryTileEntity implements IEnergyHandler, TickableBlockEntity {
    protected final EnergyStorageImpl energy;
    private final int maxExtract;

    private final ContainerData fields = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                //Minecraft actually sends fields as shorts, so we need to split energy into 2 fields
                case 0:
                    // Energy lower bytes
                    return AbstractEnergyInventoryTileEntity.this.getEnergyStored() & 0xFFFF;
                case 1:
                    // Energy upper bytes
                    return (AbstractEnergyInventoryTileEntity.this.getEnergyStored() >> 16) & 0xFFFF;
                case 2:
                    // Max energy lower bytes
                    return AbstractEnergyInventoryTileEntity.this.getMaxEnergyStored() & 0xFFFF;
                case 3:
                    // Max energy upper bytes
                    return (AbstractEnergyInventoryTileEntity.this.getMaxEnergyStored() >> 16) & 0xFFFF;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            getEnergyImpl().setEnergyDirectly(value);
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    protected AbstractEnergyInventoryTileEntity(BlockEntityType<?> typeIn, int inventorySize, int maxEnergy, int maxReceive, int maxExtract) {
        super(typeIn, inventorySize);
        this.energy = new EnergyStorageImpl(maxEnergy, maxReceive, maxExtract, this);
        this.maxExtract = maxExtract;
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return energy;
    }

    public ContainerData getFields() {
        return fields;
    }

    @Override
    public void tick() {
        if (this.level == null || level.isClientSide) return;

        if (maxExtract > 0) {
            EnergyUtils.trySendToNeighbors(this.level, worldPosition, this, maxExtract);
        }
    }

    @Override
    public void load(BlockState state, CompoundTag tags) {
        super.load(state, tags);
        SyncVariable.Helper.readSyncVars(this, tags);
        readEnergy(tags);
    }

    @Override
    public CompoundTag save(CompoundTag tags) {
        super.save(tags);
        SyncVariable.Helper.writeSyncVars(this, tags, SyncVariable.Type.WRITE);
        writeEnergy(tags);
        return tags;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        super.onDataPacket(net, packet);
        SyncVariable.Helper.readSyncVars(this, packet.getTag());
        readEnergy(packet.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tags = super.getUpdateTag();
        SyncVariable.Helper.writeSyncVars(this, tags, SyncVariable.Type.PACKET);
        writeEnergy(tags);
        return tags;
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == CapabilityEnergy.ENERGY) {
            return getEnergy(side).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        energy.invalidate();
    }
}
