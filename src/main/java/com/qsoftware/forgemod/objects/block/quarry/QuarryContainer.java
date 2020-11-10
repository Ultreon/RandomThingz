package com.qsoftware.forgemod.objects.block.quarry;

import com.qsoftware.forgemod.init.ModContainers;
import com.qsoftware.forgemod.objects.block.AbstractMachineBaseContainer;
import com.qsoftware.silent.lib.util.InventoryUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class QuarryContainer extends AbstractMachineBaseContainer<QuarryTileEntity> {
    public QuarryContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new QuarryTileEntity(), new IntArray(QuarryTileEntity.FIELDS_COUNT));
    }

    public int getCurrentX() {
        int upper = fields.get(7) & 0xFFFF;
        int lower = fields.get(6) & 0xFFFF;
        return (upper << 16) + lower;
    }

    public int getCurrentY() {
        int upper = fields.get(9) & 0xFFFF;
        int lower = fields.get(8) & 0xFFFF;
        return (upper << 16) + lower;
    }

    public int getCurrentZ() {
        int upper = fields.get(11) & 0xFFFF;
        int lower = fields.get(10) & 0xFFFF;
        return (upper << 16) + lower;
    }

    public int getBlocksRemaining() {
        int upper = fields.get(15) & 0xFFFF;
        int lower = fields.get(14) & 0xFFFF;
        return (upper << 16) + lower;
    }

    public int getTotalBlocks() {
        int upper = fields.get(17) & 0xFFFF;
        int lower = fields.get(16) & 0xFFFF;
        return (upper << 16) + lower;
    }

    public boolean isInitialized() {
        int value = fields.get(12) & 0xFFFF;
        return value == 1;
    }

    public boolean isDone() {
        int value = fields.get(13) & 0xFFFF;
        return value == 1;
    }

    public boolean isIllegalPosition() {
        int value = fields.get(18) & 0xFFFF;
        return value == 1;
    }

    public QuarryContainer(int id, PlayerInventory playerInventory, QuarryTileEntity tileEntity, IIntArray fields) {
        super(ModContainers.quarry, id, tileEntity, fields);

        InventoryUtils.createPlayerSlots(playerInventory, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

//    @SuppressWarnings("deprecation") // Use of Registry
//    public FluidStack getFluidInTank() {
//        int fluidId = this.fields.get(7);
//        Fluid fluid = Registry.FLUID.getByValue(fluidId);
//        int amount = this.fields.get(8);
//        return new FluidStack(fluid, amount);
//    }

//    @Override
//    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
//        ItemStack itemstack = ItemStack.EMPTY;
//        Slot slot = this.inventorySlots.get(index);
//        if (slot != null && slot.getHasStack()) {
//            ItemStack itemstack1 = slot.getStack();
//            itemstack = itemstack1.copy();
//
//            final int inventorySize = this.tileEntity.getSizeInventory();
//            final int playerInventoryEnd = inventorySize + 27;
//            final int playerHotbarEnd = playerInventoryEnd + 9;
//
//            if (index == 1) {
//                if (!this.mergeItemStack(itemstack1, inventorySize, playerHotbarEnd, true)) {
//                    return ItemStack.EMPTY;
//                }
//
//                slot.onSlotChange(itemstack1, itemstack);
//            } else if (index >= inventorySize) {
//                if (InventoryUtils.isEmptyFluidContainer(itemstack1) || InventoryUtils.isFilledFluidContainer(itemstack1)) {
//                    if (!this.mergeItemStack(itemstack1, 0, inventorySize - 1, false)) {
//                        return ItemStack.EMPTY;
//                    }
//                } else if (index < playerInventoryEnd) {
//                    if (!this.mergeItemStack(itemstack1, playerInventoryEnd, playerHotbarEnd, false)) {
//                        return ItemStack.EMPTY;
//                    }
//                } else if (index < playerHotbarEnd && !this.mergeItemStack(itemstack1, inventorySize, playerInventoryEnd, false)) {
//                    return ItemStack.EMPTY;
//                }
//            } else if (!this.mergeItemStack(itemstack1, inventorySize, playerHotbarEnd, false)) {
//                return ItemStack.EMPTY;
//            }
//
//            if (itemstack1.isEmpty()) {
//                slot.putStack(ItemStack.EMPTY);
//            } else {
//                slot.onSlotChanged();
//            }
//
//            if (itemstack1.getCount() == itemstack.getCount()) {
//                return ItemStack.EMPTY;
//            }
//
//            slot.onTake(playerIn, itemstack1);
//        }
//
//        return itemstack;
//    }
}
