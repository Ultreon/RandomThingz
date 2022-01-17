package com.ultreon.randomthingz.inventory.container;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base container class.
 *
 * @author Qboi123
 */
public abstract class ContainerBase extends AbstractContainerMenu {

    protected AbstractContainerMenu inventory;
    protected AbstractContainerMenu playerInventory;

    public ContainerBase(MenuType type, int id, AbstractContainerMenu playerInventory, AbstractContainerMenu inventory) {
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
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }

    @Nullable
    public AbstractContainerMenu getInventory() {
        return playerInventory;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemStack = stack.copy();

            if (index < getInventorySize()) {
                if (!this.moveItemStackTo(stack, getInventorySize(), slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, getInventorySize(), false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }

}
