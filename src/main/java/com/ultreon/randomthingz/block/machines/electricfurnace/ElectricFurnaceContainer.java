package com.ultreon.randomthingz.block.machines.electricfurnace;

import com.ultreon.modlib.embedded.silentlib.inventory.SlotOutputOnly;
import com.ultreon.modlib.embedded.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block.machines.AbstractMachineContainer;
import com.ultreon.randomthingz.block.machines.AbstractMachineTileEntity;
import com.ultreon.randomthingz.init.ModMachineContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ElectricFurnaceContainer extends AbstractMachineContainer<ElectricFurnaceTileEntity> {
    public ElectricFurnaceContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, null, new SimpleContainerData(AbstractMachineTileEntity.FIELDS_COUNT));
    }

    public ElectricFurnaceContainer(int id, Inventory playerInventory, @Nullable ElectricFurnaceTileEntity tileEntity, ContainerData fieldsIn) {
        super(ModMachineContainers.electricFurnace, id, tileEntity, fieldsIn);

        if (tileEntity != null) {
            this.addSlot(new Slot(tileEntity, 0, 56, 35));
            this.addSlot(new SlotOutputOnly(tileEntity, 1, 117, 35));
        }

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

            final int inventorySize = 2;
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index == 1) {
                if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 0) {
                if (this.isSmeltingIngredient(itemstack1)) {
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

    private boolean isSmeltingIngredient(ItemStack stack) {
        // TODO
        return true;
    }
}
