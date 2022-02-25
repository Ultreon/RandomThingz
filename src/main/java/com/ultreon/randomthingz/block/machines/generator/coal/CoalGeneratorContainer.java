package com.ultreon.randomthingz.block.machines.generator.coal;

import com.ultreon.modlib.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block.machines.MachineBlockEntity;
import com.ultreon.randomthingz.block.machines.BaseMachineBaseContainer;
import com.ultreon.randomthingz.init.ModMachineContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CoalGeneratorContainer extends BaseMachineBaseContainer<CoalGeneratorBlockEntity> {
    final CoalGeneratorBlockEntity tileEntity;

    public CoalGeneratorContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, new CoalGeneratorBlockEntity(), new SimpleContainerData(MachineBlockEntity.FIELDS_COUNT));
    }

    public CoalGeneratorContainer(int id, Inventory playerInventory, CoalGeneratorBlockEntity tileEntity, ContainerData fieldsIn) {
        super(ModMachineContainers.coalGenerator, id, tileEntity, fieldsIn);
        this.tileEntity = tileEntity;

        this.addSlot(new Slot(this.tileEntity, 0, 80, 33));

        InventoryUtils.createPlayerSlots(playerInventory, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

    public int getBurnTime() {
        return fields.get(6);
    }

    public int getTotalBurnTime() {
        return fields.get(7);
    }

    public boolean isBurning() {
        return getBurnTime() > 0;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            final int inventorySize = 1;
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index != 0) {
                if (this.isFuel(itemstack1)) {
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

    private boolean isFuel(ItemStack stack) {
        return CoalGeneratorBlockEntity.isFuel(stack);
    }
}
