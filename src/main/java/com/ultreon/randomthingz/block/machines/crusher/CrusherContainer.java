package com.ultreon.randomthingz.block.machines.crusher;

import com.ultreon.modlib.silentlib.inventory.SlotOutputOnly;
import com.ultreon.modlib.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block._common.MachineType;
import com.ultreon.randomthingz.block.machines.MachineBlockEntity;
import com.ultreon.randomthingz.block.machines.MachineContainer;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CrusherContainer extends MachineContainer<CrusherBlockEntity> {
    public CrusherContainer(int id, Inventory playerInventory, MachineTier tier) {
        this(id, playerInventory, MachineType.CRUSHER.create(tier), new SimpleContainerData(MachineBlockEntity.FIELDS_COUNT));
    }

    public CrusherContainer(int id, Inventory playerInventory, CrusherBlockEntity tileEntity, ContainerData fieldsIn) {
        super(MachineType.CRUSHER.getContainerType(tileEntity.getMachineTier()), id, tileEntity, fieldsIn);

        this.addSlot(new Slot(this.tileEntity, 0, 26, 35));
        this.addSlot(new SlotOutputOnly(this.tileEntity, 1, 80, 35));
        this.addSlot(new SlotOutputOnly(this.tileEntity, 2, 98, 35));
        this.addSlot(new SlotOutputOnly(this.tileEntity, 3, 116, 35));
        this.addSlot(new SlotOutputOnly(this.tileEntity, 4, 134, 35));

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

            final int inventorySize = 5;
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index > 0 && index < inventorySize) {
                if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 0) {
                if (this.isCrushingIngredient(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
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

    private boolean isCrushingIngredient(ItemStack stack) {
        // TODO
        return true;
    }
}
