package com.ultreon.randomthingz.block.machines;

import com.ultreon.modlib.api.RedstoneMode;
import com.ultreon.modlib.api.holders.ItemHolder;
import com.ultreon.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.item.MachineUpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public abstract class AbstractMachineBaseBlockEntity extends AbstractBaseEnergyInventoryBlockEntity {
    public static final int FIELDS_COUNT = 5;
    protected MachineTier tier;

    protected RedstoneMode redstoneMode = RedstoneMode.IGNORED;

    protected final ContainerData fields = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                //Minecraft actually sends fields as shorts, so we need to split energy into 2 fields
                case 0 ->
                        // Energy lower bytes
                        AbstractMachineBaseBlockEntity.this.getEnergyStored() & 0xFFFF;
                case 1 ->
                        // Energy upper bytes
                        (AbstractMachineBaseBlockEntity.this.getEnergyStored() >> 16) & 0xFFFF;
                case 2 ->
                        // Max energy lower bytes
                        AbstractMachineBaseBlockEntity.this.getMaxEnergyStored() & 0xFFFF;
                case 3 ->
                        // Max energy upper bytes
                        (AbstractMachineBaseBlockEntity.this.getMaxEnergyStored() >> 16) & 0xFFFF;
                case 4 -> AbstractMachineBaseBlockEntity.this.redstoneMode.ordinal();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 4) {
                AbstractMachineBaseBlockEntity.this.redstoneMode = EnumUtils.byOrdinal(value, RedstoneMode.IGNORED);
            }
        }

        @Override
        public int getCount() {
            return FIELDS_COUNT;
        }
    };

    protected AbstractMachineBaseBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state, int inventorySize, int maxEnergy, int maxReceive, int maxExtract, MachineTier tier) {
        super(typeIn, pos, state, inventorySize + tier.getUpgradeSlots(), maxEnergy, maxReceive, maxExtract);
        this.tier = tier;
    }

    public RedstoneMode getRedstoneMode() {
        return redstoneMode;
    }

    public void setRedstoneMode(RedstoneMode redstoneMode) {
        this.redstoneMode = redstoneMode;
    }

    public MachineTier getMachineTier() {
        return tier;
    }

    public int getUpgradeCount(ItemHolder upgradeItem) {
        int count = 0;
        for (int i = getContainerSize() - tier.getUpgradeSlots(); i < getContainerSize(); ++i) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty() && stack.getItem() == upgradeItem.asItem()) {
                count += stack.getCount();
            }
        }
        return count;
    }

    protected float getUpgradesEnergyMultiplier() {
        float cost = 1f;
        for (int i = getContainerSize() - tier.getUpgradeSlots(); i < getContainerSize(); ++i) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof MachineUpgradeItem) {
                cost += stack.getCount() * ((MachineUpgradeItem) stack.getItem()).getUpgrade().getEnergyUsageMultiplier();
            }
        }
        return cost;
    }

    @Override
    public ContainerData getFields() {
        return fields;
    }

    @Override
    public void load(CompoundTag tags) {
        super.load(tags);
        this.redstoneMode = EnumUtils.byOrdinal(tags.getByte("RedstoneMode"), RedstoneMode.IGNORED);
    }

    @Override
    public CompoundTag save(CompoundTag tags) {
        super.save(tags);
        tags.putByte("RedstoneMode", (byte) this.redstoneMode.ordinal());
        return tags;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        super.onDataPacket(net, packet);
        CompoundTag tags = packet.getTag();
        this.redstoneMode = EnumUtils.byOrdinal(Objects.requireNonNull(tags).getByte("RedstoneMode"), RedstoneMode.IGNORED);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tags = super.getUpdateTag();
        tags.putByte("RedstoneMode", (byte) this.redstoneMode.ordinal());
        return tags;
    }
}
