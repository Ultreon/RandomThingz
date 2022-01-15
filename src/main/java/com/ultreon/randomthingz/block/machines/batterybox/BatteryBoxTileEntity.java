package com.ultreon.randomthingz.block.machines.batterybox;

import com.ultreon.randomthingz.block.entity.ModMachineTileEntities;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseTileEntity;
import com.ultreon.randomthingz.capability.EnergyStorageImpl;
import com.ultreon.randomthingz.capability.EnergyStorageWithBatteries;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class BatteryBoxTileEntity extends AbstractMachineBaseTileEntity {
    public static final int MAX_ENERGY = 500_000;
    public static final int MAX_RECEIVE = 500;
    public static final int MAX_SEND = 500;

    static final int INVENTORY_SIZE = 6;
    private static final int[] SLOTS = IntStream.range(0, INVENTORY_SIZE).toArray();

    private final EnergyStorageWithBatteries<BatteryBoxTileEntity> energy;

    public BatteryBoxTileEntity() {
        super(ModMachineTileEntities.batteryBox, 6, MAX_ENERGY, MAX_RECEIVE, MAX_SEND, MachineTier.BASIC);
        this.energy = new EnergyStorageWithBatteries<>(this, MAX_ENERGY, MAX_RECEIVE, MAX_SEND);
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
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new BatteryBoxContainer(id, playerInventory, this, this.getFields());
    }

    @Override
    public void writeEnergy(CompoundTag tags) {
        tags.putInt("Energy", energy.getInternalEnergyStored());
    }
}
