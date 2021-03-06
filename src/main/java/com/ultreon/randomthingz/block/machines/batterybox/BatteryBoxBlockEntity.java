package com.ultreon.randomthingz.block.machines.batterybox;

import com.ultreon.randomthingz.init.ModBlocks;
import com.ultreon.randomthingz.block.entity.ModMachines;
import com.ultreon.randomthingz.block.machines.MachineBaseBlockEntity;
import com.ultreon.randomthingz.capability.EnergyStorageImpl;
import com.ultreon.randomthingz.capability.EnergyStorageWithBatteries;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class BatteryBoxBlockEntity extends MachineBaseBlockEntity {
    public static final int MAX_ENERGY = 500_000;
    public static final int MAX_RECEIVE = 500;
    public static final int MAX_SEND = 500;

    static final int INVENTORY_SIZE = 6;
    private static final int[] SLOTS = IntStream.range(0, INVENTORY_SIZE).toArray();

    private final EnergyStorageWithBatteries<BatteryBoxBlockEntity> energy;

    public BatteryBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.BATTERY_BOX.get(), pos, state, 6, MAX_ENERGY, MAX_RECEIVE, MAX_SEND, MachineTier.BASIC);
        this.energy = new EnergyStorageWithBatteries<>(this, MAX_ENERGY, MAX_RECEIVE, MAX_SEND);
    }

    public BatteryBoxBlockEntity() {
        this(BlockPos.ZERO, ModBlocks.BATTERY_BOX.asBlockState());
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return energy;
    }

    @Override
    public void tick() {
        super.tick();

        if (level == null || level.isClientSide) return;

        if (energy.getInternalEnergyStored() > 0) {
            // Charge stored batteries
            energy.receiveEnergy(energy.extractInternalEnergy(MAX_SEND / 2, false), false);
        }

        if (level.getGameTime() % 50 == 0) {
            int batteryCount = 0;
            for (int i = 0; i < getContainerSize(); ++i) {
                if (!getItem(i).isEmpty()) {
                    ++batteryCount;
                }
            }

            int currentCount = level.getBlockState(worldPosition).getValue(BatteryBoxBlock.BATTERIES);
            if (batteryCount != currentCount) {
                level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(BatteryBoxBlock.BATTERIES, batteryCount), 3);
            }
        }
    }

    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return itemStackIn.getCapability(CapabilityEnergy.ENERGY).isPresent();
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return false;
    }

    @Override
    protected Component getDefaultName() {
        return TextUtils.translate("container", "battery_box");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new BatteryBoxContainer(id, inv, inventory, worldPosition, fields);
    }

    @Override
    public void writeEnergy(CompoundTag tags) {
        tags.putInt("Energy", energy.getInternalEnergyStored());
    }
}
