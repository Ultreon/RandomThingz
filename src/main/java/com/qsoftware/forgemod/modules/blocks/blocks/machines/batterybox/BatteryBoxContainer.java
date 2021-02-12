package com.qsoftware.forgemod.modules.blocks.blocks.machines.batterybox;

import com.qsoftware.forgemod.modules.ui.ModMachineContainers;
import com.qsoftware.forgemod.modules.blocks.blocks.machines.AbstractEnergyStorageContainer;
import com.qsoftware.forgemod.modules.blocks.blocks.machines.AbstractMachineBaseTileEntity;
import com.qsoftware.modlib.silentlib.util.InventoryUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.energy.CapabilityEnergy;

public class BatteryBoxContainer extends AbstractEnergyStorageContainer<BatteryBoxTileEntity> {
    final BatteryBoxTileEntity tileEntity;

    public BatteryBoxContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new BatteryBoxTileEntity(), new IntArray(AbstractMachineBaseTileEntity.FIELDS_COUNT));
    }

    public BatteryBoxContainer(int id, PlayerInventory playerInventory, BatteryBoxTileEntity tileEntity, IIntArray fieldsIn) {
        super(ModMachineContainers.batteryBox, id, tileEntity, fieldsIn);
        this.tileEntity = tileEntity;

        this.addSlot(new Slot(this.tileEntity, 0, 71, 19));
        this.addSlot(new Slot(this.tileEntity, 1, 71, 37));
        this.addSlot(new Slot(this.tileEntity, 2, 71, 55));
        this.addSlot(new Slot(this.tileEntity, 3, 89, 19));
        this.addSlot(new Slot(this.tileEntity, 4, 89, 37));
        this.addSlot(new Slot(this.tileEntity, 5, 89, 55));

        InventoryUtils.createPlayerSlots(playerInventory, 8, 84).forEach(this::addSlot);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stackCopy = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            stackCopy = stack.copy();

            final int inventorySize = BatteryBoxTileEntity.INVENTORY_SIZE;
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index >= inventorySize) {
                if (this.isBattery(stack)) {
                    if (!this.mergeItemStack(stack, 0, inventorySize, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerInventoryEnd) {
                    if (!this.mergeItemStack(stack, playerInventoryEnd, playerHotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerHotbarEnd && !this.mergeItemStack(stack, inventorySize, playerInventoryEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, inventorySize, playerHotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == stackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return stackCopy;
    }

    private boolean isBattery(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY).isPresent();
    }
}
