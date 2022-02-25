package com.ultreon.randomthingz.block.machines.generator;

import com.ultreon.randomthingz.block.machines.BaseMachineBaseContainer;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public abstract class AbstractFluidGeneratorContainer<T extends FluidFuelGeneratorBlockEntity> extends BaseMachineBaseContainer<T> {
    public AbstractFluidGeneratorContainer(MenuType<?> type, int id, T tileEntityIn, ContainerData fieldsIn) {
        super(type, id, tileEntityIn, fieldsIn);
    }

    public int getBurnTime() {
        return fields.get(6);
    }

    @SuppressWarnings("deprecation") // Use of Registry
    public FluidStack getFluidInTank() {
        int fluidId = this.fields.get(8);
        Fluid fluid = Registry.FLUID.byId(fluidId);
        int amount = this.fields.get(9);
        return new FluidStack(fluid, amount);
    }

    protected abstract boolean isFuel(ItemStack stack);

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            final int inventorySize = 2;
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
}
