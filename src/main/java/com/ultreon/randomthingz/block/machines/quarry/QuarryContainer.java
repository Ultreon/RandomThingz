package com.ultreon.randomthingz.block.machines.quarry;

import com.ultreon.modlib.embedded.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseContainer;
import com.ultreon.randomthingz.init.ModMachineContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class QuarryContainer extends AbstractMachineBaseContainer<QuarryTileEntity> {
    public QuarryContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, null, new SimpleContainerData(QuarryTileEntity.FIELDS_COUNT));
    }

    public QuarryContainer(int id, Inventory playerInventory, @Nullable QuarryTileEntity tileEntity, ContainerData fields) {
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
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            final int inventorySize = this.tileEntity.getContainerSize();
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index == inventorySize - 1) {
                if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index >= inventorySize) {
                if (index < playerInventoryEnd) {
                    if (!this.moveItemStackTo(itemstack1, playerInventoryEnd, playerHotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerHotbarEnd && !this.moveItemStackTo(itemstack1, inventorySize, playerInventoryEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
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
