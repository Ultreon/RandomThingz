package com.ultreon.randomthingz.block.machines.generator;

import com.ultreon.modlib.api.RedstoneMode;
import com.ultreon.modlib.embedded.silentutils.EnumUtils;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseBlockEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public abstract class GeneratorBlockEntity extends AbstractMachineBaseBlockEntity {
    public static final int FIELDS_COUNT = 7;

    protected int burnTime;
    protected int totalBurnTime;

    protected final ContainerData fields = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                //Minecraft actually sends fields as shorts, so we need to split energy into 2 fields
                case 0 ->
                        // Energy lower bytes
                        GeneratorBlockEntity.this.getEnergyStored() & 0xFFFF;
                case 1 ->
                        // Energy upper bytes
                        (GeneratorBlockEntity.this.getEnergyStored() >> 16) & 0xFFFF;
                case 2 ->
                        // Max energy lower bytes
                        GeneratorBlockEntity.this.getMaxEnergyStored() & 0xFFFF;
                case 3 ->
                        // Max energy upper bytes
                        (GeneratorBlockEntity.this.getMaxEnergyStored() >> 16) & 0xFFFF;
                case 4 -> redstoneMode.ordinal();
                case 5 -> burnTime;
                case 6 -> totalBurnTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 4 -> redstoneMode = EnumUtils.byOrdinal(value, RedstoneMode.IGNORED);
                case 5 -> burnTime = value;
                case 6 -> totalBurnTime = value;
            }
        }

        @Override
        public int getCount() {
            return FIELDS_COUNT;
        }
    };

    protected GeneratorBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state, int inventorySize, int maxEnergy, int maxReceive, int maxExtract, MachineTier tier) {
        super(typeIn, pos, state, inventorySize, maxEnergy, maxReceive, maxExtract, tier);
    }

    protected abstract boolean hasFuel();

    protected abstract void consumeFuel();

    protected abstract int getEnergyCreatedPerTick();

    protected BlockState getActiveState() {
        return getBlockState().setValue(AbstractFurnaceBlock.LIT, true);
    }

    protected BlockState getInactiveState() {
        return getBlockState().setValue(AbstractFurnaceBlock.LIT, false);
    }

    @Override
    public void tick() {
        if (canRun()) {
            if (burnTime <= 0 && hasFuel()) {
                consumeFuel();
                sendUpdate(getActiveState(), true);
            }
        }

        if (burnTime > 0) {
            --burnTime;
            energy.createEnergy(getEnergyCreatedPerTick());
        } else {
            sendUpdate(getInactiveState(), false);
        }

        super.tick();
    }

    protected boolean canRun() {
        return level != null
                && redstoneMode.shouldRun(level.hasNeighborSignal(worldPosition))
                && getEnergyStored() < getMaxEnergyStored();
    }

    protected void sendUpdate(BlockState newState, boolean force) {
        if (level == null) return;
        BlockState oldState = level.getBlockState(worldPosition);
        if (oldState != newState || force) {
            level.setBlock(worldPosition, newState, 3);
            level.sendBlockUpdated(worldPosition, oldState, newState, 3);
        }
    }

    @Override
    public void load(CompoundTag tags) {
        super.load(tags);
        this.burnTime = tags.getInt("BurnTime");
        this.totalBurnTime = tags.getInt("TotalBurnTime");
    }

    @Override
    public CompoundTag save(CompoundTag tags) {
        tags.putInt("BurnTime", this.burnTime);
        tags.putInt("TotalBurnTime", this.totalBurnTime);
        return super.save(tags);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        super.onDataPacket(net, packet);
        CompoundTag tags = packet.getTag();
        this.burnTime = Objects.requireNonNull(tags).getInt("BurnTime");
        this.totalBurnTime = tags.getInt("TotalBurnTime");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tags = super.getUpdateTag();
        tags.putInt("BurnTime", this.burnTime);
        tags.putInt("TotalBurnTime", this.totalBurnTime);
        return tags;
    }
}
