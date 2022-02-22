package com.ultreon.randomthingz.block.machines.alloysmelter;

import com.ultreon.modlib.silentlib.inventory.SlotOutputOnly;
import com.ultreon.modlib.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block._common.MachineType;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.AbstractMachineBlockEntity;
import com.ultreon.randomthingz.block.machines.AbstractMachineContainer;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AlloySmelterContainer extends AbstractMachineContainer<AlloySmelterBlockEntity> {
    public AlloySmelterContainer(int id, Inventory playerInventory, MachineTier tier) {
        this(id, playerInventory, MachineType.ALLOY_SMELTER.create(tier), new SimpleContainerData(AbstractMachineBlockEntity.FIELDS_COUNT));
    }

    protected AlloySmelterContainer(int id, Inventory playerInventory, AlloySmelterBlockEntity tileEntityIn, ContainerData fieldsIn) {
        super(MachineType.ALLOY_SMELTER.getContainerType(tileEntityIn.getMachineTier()), id, tileEntityIn, fieldsIn);

        if (this.tileEntity == null) {
            return;
        }

        for (int i = 0; i < AlloySmelterBlockEntity.INPUT_SLOT_COUNT; ++i) {
            this.addSlot(new Slot(this.tileEntity, i, 17 + 18 * i, 35));
        }
        this.addSlot(new SlotOutputOnly(this.tileEntity, AlloySmelterBlockEntity.INPUT_SLOT_COUNT, 126, 35));

        InventoryUtils.createPlayerSlots(playerInventory, 8, 84).forEach(this::addSlot);
        this.addUpgradeSlots();
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (this.tileEntity == null) {
                return itemstack;
            }

            final int inventorySize = this.tileEntity.getContainerSize();
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index == inventorySize - 1) {
                if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index >= inventorySize) {
                if (this.isAlloySmeltingIngredient(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, inventorySize - 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerInventoryEnd) {
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

    private boolean isAlloySmeltingIngredient(ItemStack stack) {
        // TODO
        return true;
    }
}
