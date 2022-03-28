package com.ultreon.randomthingz.block.machines;

import com.ultreon.modlib.block.entity.InventoryBlockEntity;
import com.ultreon.modlib.silentlib.block.entity.SyncVariable;
import com.ultreon.randomthingz.capability.EnergyStorageImpl;
import com.ultreon.randomthingz.util.EnergyUtils;
import com.ultreon.modlib.block.entity.Tickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class BaseEnergyInventoryBlockEntity extends InventoryBlockEntity implements EnergyHandler, Tickable {
    protected final EnergyStorageImpl energy;
    private final int maxExtract;

    private final ContainerData fields = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                //Minecraft actually sends fields as shorts, so we need to split energy into 2 fields
                case 0 ->
                        // Energy lower bytes
                        BaseEnergyInventoryBlockEntity.this.getEnergyStored() & 0xFFFF;
                case 1 ->
                        // Energy upper bytes
                        (BaseEnergyInventoryBlockEntity.this.getEnergyStored() >> 16) & 0xFFFF;
                case 2 ->
                        // Max energy lower bytes
                        BaseEnergyInventoryBlockEntity.this.getMaxEnergyStored() & 0xFFFF;
                case 3 ->
                        // Max energy upper bytes
                        (BaseEnergyInventoryBlockEntity.this.getMaxEnergyStored() >> 16) & 0xFFFF;
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
    protected BaseEnergyInventoryBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state, int inventorySize, int maxEnergy, int maxReceive, int maxExtract) {
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
    public void tick(Level level, BlockPos pos, BlockState state) {
        this.tick();
    }

    @Override
    public void load(CompoundTag tags) {
        super.load(tags);
        SyncVariable.Helper.readSyncVars(this, tags);
        readEnergy(tags);
    }

    @Override
    protected abstract Component getDefaultName();

    @Override
    protected abstract AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_);

    @Override
    public void saveAdditional(CompoundTag tags) {
        super.save(tags);
        SyncVariable.Helper.writeSyncVars(this, tags, SyncVariable.Type.SAVE);
        writeEnergy(tags);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        super.onDataPacket(net, packet);
        SyncVariable.Helper.readSyncVars(this, Objects.requireNonNull(packet.getTag()));
        readEnergy(packet.getTag());
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
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

    @Override
    public abstract int[] getSlotsForFace(Direction p_19238_);

    @Override
    public abstract boolean canPlaceItemThroughFace(int p_19235_, ItemStack p_19236_, @Nullable Direction p_19237_);

    @Override
    public abstract boolean canTakeItemThroughFace(int p_19239_, ItemStack p_19240_, Direction p_19241_);
}
