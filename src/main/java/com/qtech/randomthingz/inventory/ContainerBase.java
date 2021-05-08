package com.qtech.randomthingz.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Base container class.
 *
 * @author Qboi123
 */
public abstract class ContainerBase extends Container {

    protected IInventory inventory;
    protected IInventory playerInventory;

    public ContainerBase(ContainerType type, int id, IInventory playerInventory, IInventory inventory) {
        super(type, id);
        this.playerInventory = playerInventory;
        this.inventory = inventory;
    }

    protected void addInvSlots() {
        addInvSlots(0);
    }

    @SuppressWarnings("SameParameterValue")
    protected void addInvSlots(int dy) {
        addInvSlots(0, dy);
    }

    @SuppressWarnings("SameParameterValue")
    protected void addInvSlots(int dx, int dy) {
        if (playerInventory != null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                    addSlot(new Slot(playerInventory, j + i * 9 + 9, dx + (8 + j * 18), dy + (84 + i * 18 + getInvOffset())));
                }
            }

            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(playerInventory, k, dx + (8 + k * 18), dy + (142 + getInvOffset())));
            }
        }
    }

    public int getInvOffset() {
        return 0;
    }

    public abstract int getInventorySize();

    @Override
    public boolean canInteractWith(@NotNull PlayerEntity playerIn) {
        return true;
    }

    @Nullable
    public IInventory getPlayerInventory() {
        return playerInventory;
    }

    @Override
    public @NotNull ItemStack transferStackInSlot(@NotNull PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.hasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();

            if (index < getInventorySize()) {
                if (!this.mergeItemStack(stack, getInventorySize(), inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 0, getInventorySize(), false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemStack;
    }

}
