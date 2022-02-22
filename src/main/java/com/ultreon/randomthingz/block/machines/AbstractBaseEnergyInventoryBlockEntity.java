package com.ultreon.randomthingz.block.machines;

import com.ultreon.modlib.silentlib.block.entity.BaseInventoryContainerBlockEntity;
import com.ultreon.modlib.silentlib.block.entity.SyncVariable;
import com.ultreon.randomthingz.capability.EnergyStorageImpl;
import com.ultreon.randomthingz.util.EnergyUtils;
import com.ultreon.texturedmodels.tileentity.ITickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class AbstractBaseEnergyInventoryBlockEntity extends BaseInventoryContainerBlockEntity implements EnergyHandler, ITickable {
    protected final EnergyStorageImpl energy;
    private final int maxExtract;

    private final ContainerData fields = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                //Minecraft actually sends fields as shorts, so we need to split energy into 2 fields
                case 0 ->
                        // Energy lower bytes
                        AbstractBaseEnergyInventoryBlockEntity.this.getEnergyStored() & 0xFFFF;
                case 1 ->
                        // Energy upper bytes
                        (AbstractBaseEnergyInventoryBlockEntity.this.getEnergyStored() >> 16) & 0xFFFF;
                case 2 ->
                        // Max energy lower bytes
                        AbstractBaseEnergyInventoryBlockEntity.this.getMaxEnergyStored() & 0xFFFF;
                case 3 ->
                        // Max energy upper bytes
                        (AbstractBaseEnergyInventoryBlockEntity.this.getMaxEnergyStored() >> 16) & 0xFFFF;
                default -> 0;
            };
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

    protected AbstractBaseEnergyInventoryBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state, int inventorySize, int maxEnergy, int maxReceive, int maxExtract) {
        super(typeIn, pos, state, inventorySize);
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
    public void load(CompoundTag tags) {
        super.load(tags);
        SyncVariable.Helper.readSyncVars(this, tags);
        readEnergy(tags);
    }

    @Override
    public CompoundTag save(CompoundTag tags) {
        super.save(tags);
        SyncVariable.Helper.writeSyncVars(this, tags, SyncVariable.Type.SAVE);
        writeEnergy(tags);
        return tags;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        super.onDataPacket(net, packet);
        SyncVariable.Helper.readSyncVars(this, Objects.requireNonNull(packet.getTag()));
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
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
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
