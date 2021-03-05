package com.qsoftware.forgemod.modules.tiles.blocks.machines.quarry;

import com.qsoftware.forgemod.modules.tiles.blocks.machines.AbstractMachineBaseContainer;
import com.qsoftware.forgemod.modules.ui.ModMachineContainers;
import com.qsoftware.modlib.silentlib.util.InventoryUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class QuarryContainer extends AbstractMachineBaseContainer<QuarryTileEntity> {
    public QuarryContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new QuarryTileEntity(), new IntArray(QuarryTileEntity.FIELDS_COUNT));
    }

    public QuarryContainer(int id, PlayerInventory playerInventory, QuarryTileEntity tileEntity, IIntArray fields) {
        super(ModMachineContainers.quarry, id, tileEntity, fields);

        InventoryUtils.createPlayerSlots(playerInventory, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
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

//    public boolean isInitialized() {
//        int value = fields.get(12) & 0xFFFF;
//        return value == 1;
//    }

//    public boolean isDone() {
//        int value = fields.get(13) & 0xFFFF;
//        return value == 1;
//    }

    public QuarryTileEntity.Status getStatus() {
        int upper = fields.get(21) & 0xFFFF;
        int lower = fields.get(20) & 0xFFFF;
        int i = (upper << 16) + lower;
        return QuarryTileEntity.Status.values()[i];
    }

//    public boolean isIllegalPosition() {
//        int value = fields.get(18) & 0xFFFF;
//        return value == 1;
//    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            final int inventorySize = this.tileEntity.getSizeInventory();
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index == inventorySize - 1) {
                if (!this.mergeItemStack(itemstack1, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= inventorySize) {
                if (index < playerInventoryEnd) {
                    if (!this.mergeItemStack(itemstack1, playerInventoryEnd, playerHotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerHotbarEnd && !this.mergeItemStack(itemstack1, inventorySize, playerInventoryEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, inventorySize, playerHotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    public BlockPos getCurrentPos() {
        return new BlockPos(getCurrentX(), getCurrentY(), getCurrentZ());
    }
}
