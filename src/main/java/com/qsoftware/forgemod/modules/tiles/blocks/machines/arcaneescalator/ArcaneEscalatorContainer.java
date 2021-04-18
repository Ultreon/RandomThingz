package com.qsoftware.forgemod.modules.tiles.blocks.machines.arcaneescalator;

import com.qsoftware.forgemod.common.enums.MachineTier;
import com.qsoftware.forgemod.modules.tiles.MachineType;
import com.qsoftware.forgemod.modules.tiles.blocks.machines.AbstractMachineContainer;
import com.qsoftware.forgemod.modules.tiles.blocks.machines.AbstractMachineTileEntity;
import com.qsoftware.modlib.silentlib.inventory.SlotOutputOnly;
import com.qsoftware.modlib.silentlib.util.InventoryUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

@SuppressWarnings("ConstantConditions")
public class ArcaneEscalatorContainer extends AbstractMachineContainer<ArcaneEscalatorTileEntity> {
    public ArcaneEscalatorContainer(int id, PlayerInventory playerInventory, MachineTier tier) {
        this(id, playerInventory, MachineType.ARCANE_ESCALATOR.create(tier), new IntArray(AbstractMachineTileEntity.FIELDS_COUNT));
    }

    protected ArcaneEscalatorContainer(int id, PlayerInventory playerInventory, ArcaneEscalatorTileEntity tileEntityIn, IIntArray fieldsIn) {
        super(MachineType.ARCANE_ESCALATOR.getContainerType(tileEntityIn.getMachineTier()), id, tileEntityIn, fieldsIn);

        for (int i = 0; i < ArcaneEscalatorTileEntity.INPUT_SLOT_COUNT; ++i) {
            this.addSlot(new Slot(this.tileEntity, i, 17 + 18 * i, 35));
        }
        this.addSlot(new SlotOutputOnly(this.tileEntity, ArcaneEscalatorTileEntity.INPUT_SLOT_COUNT, 126, 35));

        InventoryUtils.createPlayerSlots(playerInventory, 8, 84).forEach(this::addSlot);
        this.addUpgradeSlots();
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.hasStack()) {
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
                if (this.isArcaneEscalatingIngredient(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, inventorySize - 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerInventoryEnd) {
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

    private boolean isArcaneEscalatingIngredient(ItemStack stack) {
        // TODO
        return true;
    }
}
