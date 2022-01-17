package com.ultreon.randomthingz.block.machines.refinery;

import com.ultreon.modlib.embedded.silentlib.inventory.SlotOutputOnly;
import com.ultreon.modlib.embedded.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseContainer;
import com.ultreon.randomthingz.init.ModMachineContainers;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class RefineryContainer extends AbstractMachineBaseContainer<RefineryTileEntity> {
    public RefineryContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, null, new SimpleContainerData(RefineryTileEntity.FIELDS_COUNT));
    }

    public RefineryContainer(int id, Inventory playerInventory, @Nullable RefineryTileEntity tileEntity, ContainerData fieldsIn) {
        super(ModMachineContainers.refinery, id, tileEntity, fieldsIn);

        if (this.tileEntity != null) {
            this.addSlot(new Slot(this.tileEntity, 0, 8, 16));
            this.addSlot(new SlotOutputOnly(this.tileEntity, 1, 8, 59));
            this.addSlot(new Slot(this.tileEntity, 2, 134, 16));
            this.addSlot(new SlotOutputOnly(this.tileEntity, 3, 134, 59));
        }

        InventoryUtils.createPlayerSlots(playerInventory, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

    public int getProgress() {
        return fields.get(5);
    }

    public int getProcessTime() {
        return fields.get(6);
    }

    @SuppressWarnings("deprecation") // Use of Registry
    public FluidStack getFluidInTank(int tank) {
        int fluidId = this.fields.get(7 + 2 * tank);
        Fluid fluid = Registry.FLUID.byId(fluidId);
        int amount = this.fields.get(8 + 2 * tank);
        return new FluidStack(fluid, amount);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack emptyStack = ItemStack.EMPTY;
        if (tileEntity == null) return emptyStack;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            emptyStack = slotStack.copy();

            final int size = this.tileEntity.getContainerSize();
            final int invEnd = size + 27;
            final int hotbarEnd = invEnd + 9;

            if (index == 1 || index == 3) {
                if (!this.moveItemStackTo(slotStack, size, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(slotStack, emptyStack);
            } else if (index >= size) {
                if (com.ultreon.randomthingz.util.InventoryUtils.isEmptyFluidContainer(slotStack) || com.ultreon.randomthingz.util.InventoryUtils.isFilledFluidContainer(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 0, size - 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < invEnd) {
                    if (!this.moveItemStackTo(slotStack, invEnd, hotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < hotbarEnd && !this.moveItemStackTo(slotStack, size, invEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, size, hotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == emptyStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
        }

        return emptyStack;
    }
}
