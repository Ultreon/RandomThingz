package com.qsoftware.forgemod.container;

import com.qsoftware.forgemod.modules.blocks.ModBlocks;
import com.qsoftware.forgemod.modules.ui.ModContainers;
import com.qsoftware.forgemod.modules.tileentities.objects.CrateTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Crate container class.
 *
 * @author Qboi123
 */
public class CrateContainer extends Container {
    public final CrateTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;

    public CrateContainer(final int windowId, final PlayerInventory playerInventory, final CrateTileEntity tileEntity) {
        super(ModContainers.WOODEN_CRATE.get(), windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos());

        // QForgeUtils Inventory
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

        // QForgeUtils Player Inventory
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

    public CrateContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    private static CrateTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
//        return tileEntity;
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());

        if (tileAtPos instanceof CrateTileEntity) {
            return (CrateTileEntity) tileAtPos;
        }

        throw new IllegalStateException("Tile entity is not of the expected class! " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(@NotNull PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, ModBlocks.WOODEN_CRATE.get());
    }

    @Override
    public @NotNull ItemStack transferStackInSlot(@NotNull PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            if (index < 36) {
                if (this.mergeItemStack(itemStack1, 36, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemStack1, 0, 36, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1 == ItemStack.EMPTY) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemStack;
    }
}
