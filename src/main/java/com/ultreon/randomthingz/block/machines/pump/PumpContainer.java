package com.ultreon.randomthingz.block.machines.pump;

import com.ultreon.randomthingz.block.machines.AbstractMachineBaseContainer;
import com.ultreon.randomthingz.init.ModMachineContainers;
import com.ultreon.randomthingz.util.InventoryUtils;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class PumpContainer extends AbstractMachineBaseContainer<PumpTileEntity> {
    public PumpContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, new PumpTileEntity(), new SimpleContainerData(PumpTileEntity.FIELDS_COUNT));
    }

    public PumpContainer(int id, Inventory playerInventory, PumpTileEntity tileEntity, ContainerData fields) {
        super(ModMachineContainers.pump, id, tileEntity, fields);

        this.addSlot(new Slot(this.tileEntity, 0, 80, 16));
        this.addSlot(new Slot(this.tileEntity, 1, 80, 59));

        com.qsoftware.modlib.silentlib.util.InventoryUtils.createPlayerSlots(playerInventory, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

    @SuppressWarnings("deprecation") // Use of Registry
    public FluidStack getFluidInTank() {
        int fluidId = this.fields.get(7);
        Fluid fluid = Registry.FLUID.byId(fluidId);
        int amount = this.fields.get(8);
        return new FluidStack(fluid, amount);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            final int inventorySize = this.tileEntity.getContainerSize();
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index == 1) {
                if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index >= inventorySize) {
                if (InventoryUtils.isEmptyFluidContainer(itemstack1) || InventoryUtils.isFilledFluidContainer(itemstack1)) {
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
}
