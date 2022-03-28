package com.ultreon.randomthingz.inventory.container;

import com.ultreon.randomthingz.init.ModBlocks;
import com.ultreon.randomthingz.init.ModContainers;
import com.ultreon.randomthingz.block.entity.CrateBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Crate container class.
 *
 * @author Qboi123
 */
public class CrateContainer extends AbstractContainerMenu {
    public final CrateBlockEntity tileEntity;
    private final ContainerLevelAccess canInteractWithCallable;

    public CrateContainer(final int windowId, final Inventory playerInventory, final CrateBlockEntity tileEntity) {
        super(ModContainers.WOODEN_CRATE.get(), windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = ContainerLevelAccess.create(Objects.requireNonNull(tileEntity.getLevel()), tileEntity.getBlockPos());

        // Random Thingz Inventory
        int startX = 8;
        int startY = 18;
        int slotSizePlus2 = 18;
        // This is for manually calculate index.
        for (int row = 0; row < 4; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(this.tileEntity, (row * 9) + col, startX + (col * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }

//        // This is for automatic calculate index.
//        int index = 0;
//
//        for (int row = 0; row < 4; ++row) {
//            for (int col = 0; col < 9; ++col) {
//                this.addSlot(new Slot(this.tileEntity, index, startX + (col * slotSizePlus2));
//                index++;
//            }
//        }

//        // This is for full automate calculation:
//        int slotWidth = 16;
//        int slotHeight = 16;
//        int slotBorderX = 1;
//        int slotBorderY = 1;
//        int slotMarginX = 0;
//        int slotMarginY = 0;
//        int index = 0;
//        int rows = 4;
//        int columns = 9;
//
//        // /!\ DON'T CHANGE /!\ //
//        int totalPerSlotWidth = (slotWidth + (slotBorderX * 2) + (slotMarginX * 2));
//        int totalPerSlotHeight = (slotHeight + (slotBorderY * 2) + (slotMarginY * 2));
//        int totalSlotsWidth = (totalPerSlotWidth * columns);
//        int totalSlotsHeight = (totalPerSlotHeight * rows);
//        int endX = startX + totalSlotsWidth;
//        int endY = startY + totalSlotsHeight;
//        for (int x = startX; x <= endX; x += totalPerSlotWidth) {
//            for (int y = startY; y <= endY; y += totalPerSlotHeight) {
//                this.addSlot(new Slot(this.tileEntity, index, x, y));
//                index++;
//            }
//        }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //     Player Inventory     //
        //////////////////////////////

        // Random Thingz Player Inventory
        int startPlayerInvX = 8;
        int startPlayerInvY = 102;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + col, startPlayerInvX + (col * slotSizePlus2), startPlayerInvY + (row * slotSizePlus2)));
            }
        }

        // Hotbar
        //noinspection UnnecessaryLocalVariable
        int hotBarX = startPlayerInvX;
        int hotBarY = startPlayerInvY + 58;
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, hotBarX + (col * slotSizePlus2), hotBarY));
        }
    }

    public CrateContainer(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    private static CrateBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
//        return tileEntity;
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final BlockEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());

        if (tileAtPos instanceof CrateBlockEntity) {
            return (CrateBlockEntity) tileAtPos;
        }

        throw new IllegalStateException("Tile entity is not of the expected class! " + tileAtPos);
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, ModBlocks.WOODEN_CRATE.get());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            if (index < 36) {
                if (this.moveItemStackTo(itemStack1, 36, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 0, 36, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1 == ItemStack.EMPTY) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }
}
