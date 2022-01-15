package com.ultreon.randomthingz.block.machines.generator;

import com.qsoftware.modlib.api.RedstoneMode;
import com.qsoftware.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseTileEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractGeneratorTileEntity extends AbstractMachineBaseTileEntity {
    public static final int FIELDS_COUNT = 7;

    protected int burnTime;
    protected int totalBurnTime;

    protected final ContainerData fields = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                //Minecraft actually sends fields as shorts, so we need to split energy into 2 fields
                case 0:
                    // Energy lower bytes
                    return AbstractGeneratorTileEntity.this.getEnergyStored() & 0xFFFF;
                case 1:
                    // Energy upper bytes
                    return (AbstractGeneratorTileEntity.this.getEnergyStored() >> 16) & 0xFFFF;
                case 2:
                    // Max energy lower bytes
                    return AbstractGeneratorTileEntity.this.getMaxEnergyStored() & 0xFFFF;
                case 3:
                    // Max energy upper bytes
                    return (AbstractGeneratorTileEntity.this.getMaxEnergyStored() >> 16) & 0xFFFF;
                case 4:
                    return redstoneMode.ordinal();
                case 5:
                    return burnTime;
                case 6:
                    return totalBurnTime;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 4:
                    redstoneMode = EnumUtils.byOrdinal(value, RedstoneMode.IGNORED);
                    break;
                case 5:
                    burnTime = value;
                    break;
                case 6:
                    totalBurnTime = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return FIELDS_COUNT;
        }
    };

    protected AbstractGeneratorTileEntity(BlockEntityType<?> typeIn, int inventorySize, int maxEnergy, int maxReceive, int maxExtract, MachineTier tier) {
        super(typeIn, inventorySize, maxEnergy, maxReceive, maxExtract, tier);
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
    public void load(BlockState state, CompoundTag tags) {
        super.load(state, tags);
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
        this.burnTime = tags.getInt("BurnTime");
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
