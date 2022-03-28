package com.ultreon.randomthingz.block.machines.refinery;

import com.ultreon.modlib.block.entity.UmlItemStackHandler;
import com.ultreon.modlib.inventory.OutputSlot;
import com.ultreon.modlib.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block.machines.BaseMachineContainer;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.init.ModMachineContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.SlotItemHandler;

public class RefineryContainer extends BaseMachineContainer<RefineryBlockEntity> {
    public RefineryContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, new UmlItemStackHandler(4 + MachineTier.STANDARD.getUpgradeSlots()), BlockPos.ZERO, new SimpleContainerData(RefineryBlockEntity.FIELDS_COUNT));
    }

    public RefineryContainer(int id, Inventory inv, UmlItemStackHandler stackHandler, BlockPos pos, ContainerData fieldsIn) {
        super(ModMachineContainers.refinery, id, inv, stackHandler, pos, fieldsIn);

        this.addSlot(new SlotItemHandler(stackHandler, 0, 8, 16));
        this.addSlot(new OutputSlot(stackHandler, 1, 8, 59));
        this.addSlot(new SlotItemHandler(stackHandler, 2, 134, 16));
        this.addSlot(new OutputSlot(stackHandler, 3, 134, 59));

        InventoryUtils.createPlayerSlots(inv, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.STANDARD;
    }

    public int getProgress() {
        return fields.get(6);
    }

    public int getProcessTime() {
        return fields.get(7);
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
