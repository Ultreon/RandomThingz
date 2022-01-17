package com.ultreon.texturedmodels.container;

import com.ultreon.texturedmodels.setup.Registration;
import com.ultreon.texturedmodels.tileentity.ChestFrameTileEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

/**
 * Here you can find all information about the Chest Illusion AbstractContainerMenu, like number of slots and stored itemStacks
 *
 * @author PianoManu
 * @version 1.0 09/22/20
 */
public class IllusionChestContainer extends AbstractContainerMenu {

    public final ChestFrameTileEntity tileEntity;
    private final ContainerLevelAccess canInteractWithCallable;

    public IllusionChestContainer(final int windowId, final Inventory playerInventory, final ChestFrameTileEntity tileEntity) {
        super(Registration.CHEST_ILLUSION_CONTAINER.get(), windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos());

        // Main Inventory
        int startX = 8;
        int startY = 18;
        int slotSizePlus2 = 18;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(tileEntity, (row * 9) + column, startX + (column * slotSizePlus2),
                        startY + (row * slotSizePlus2)));
            }
        }

        // Main Player Inventory
        int startPlayerInvY = startY * 4 + 13;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
                        startPlayerInvY + (row * slotSizePlus2)));
            }
        }

        // Hotbar
        int hotbarY = startPlayerInvY + (startPlayerInvY / 2) + 16;
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), hotbarY));
        }
    }

    public IllusionChestContainer(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    private static ChestFrameTileEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof ChestFrameTileEntity) {
            return (ChestFrameTileEntity) tileAtPos;
        }
        throw new IllegalStateException("BlockEntity should be of type ChestFrameTileEntity but is " + tileAtPos);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, Registration.CHEST_ILLUSIONBLOCK.get());
    }

    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            if (index < 27) {
                if (this.moveItemStackTo(itemStack1, 27, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 0, 27, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }
}
