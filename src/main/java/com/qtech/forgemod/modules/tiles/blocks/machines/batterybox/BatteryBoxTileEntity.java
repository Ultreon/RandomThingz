package com.qtech.forgemod.modules.tiles.blocks.machines.batterybox;

import com.qtech.forgemod.capability.EnergyStorageImpl;
import com.qtech.forgemod.capability.EnergyStorageWithBatteries;
import com.qtech.forgemod.modules.tiles.ModMachineTileEntities;
import com.qtech.forgemod.modules.tiles.blocks.machines.AbstractMachineBaseTileEntity;
import com.qtech.forgemod.common.enums.MachineTier;
import com.qtech.forgemod.util.TextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
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

        if (dimension == null || dimension.isClientSided) return;

        if (energy.getInternalEnergyStored() > 0) {
            // Charge stored batteries
            energy.receiveEnergy(energy.extractInternalEnergy(MAX_SEND / 2, false), false);
        }

        if (dimension.getGameTime() % 50 == 0) {
            int batteryCount = 0;
            for (int i = 0; i < getSizeInventory(); ++i) {
                if (!getStackInSlot(i).isEmpty()) {
                    ++batteryCount;
                }
            }

            int currentCount = dimension.getBlockState(pos).get(BatteryBoxBlock.BATTERIES);
            if (batteryCount != currentCount) {
                dimension.setBlockState(pos, dimension.getBlockState(pos).with(BatteryBoxBlock.BATTERIES, batteryCount), 3);
            }
        }
    }

    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return itemStackIn.getCapability(CapabilityEnergy.ENERGY).isPresent();
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return false;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return TextUtils.translate("container", "battery_box");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory) {
        return new BatteryBoxContainer(id, playerInventory, this, this.getFields());
    }

    @Override
    public void writeEnergy(CompoundNBT tags) {
        tags.putInt("Energy", energy.getInternalEnergyStored());
    }
}
