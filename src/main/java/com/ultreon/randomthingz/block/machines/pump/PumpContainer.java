package com.ultreon.randomthingz.block.machines.pump;

import com.ultreon.modlib.block.entity.UmlItemStackHandler;
import com.ultreon.randomthingz.block.machines.BaseMachineContainer;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.init.ModMachineContainers;
import com.ultreon.randomthingz.util.InventoryUtils;
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

public class PumpContainer extends BaseMachineContainer<PumpBlockEntity> {
    public PumpContainer(int id, Inventory inventory) {
        this(id, inventory, new UmlItemStackHandler(2 + MachineTier.STANDARD.getUpgradeSlots()), BlockPos.ZERO, new SimpleContainerData(PumpBlockEntity.FIELDS_COUNT));
    }

    public PumpContainer(int id, Inventory inv, UmlItemStackHandler stackHandler, BlockPos pos, ContainerData fields) {
        super(ModMachineContainers.pump, id, inv, stackHandler, pos, fields);

        this.addSlot(new SlotItemHandler(stackHandler, 0, 80, 16));
        this.addSlot(new SlotItemHandler(stackHandler, 1, 80, 59));

        com.ultreon.modlib.silentlib.util.InventoryUtils.createPlayerSlots(inv, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.STANDARD;
    }

    @SuppressWarnings("deprecation") // Use of Registry
    public FluidStack getFluidInTank() {
        int fluidId = this.fields.get(8);
        Fluid fluid = Registry.FLUID.byId(fluidId);
        int amount = this.fields.get(9);
        return new FluidStack(fluid, amount);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        if (this.tileEntity == null) return ItemStack.EMPTY;
        ItemStack emptyStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            emptyStack = slotStack.copy();

            final int size = this.tileEntity.getContainerSize();
            final int inventoryEnd = size + 27;
            final int hotbarEnd = inventoryEnd + 9;

            if (index == 1) {
                if (!this.moveItemStackTo(slotStack, size, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(slotStack, emptyStack);
            } else if (index >= size) {
                if (InventoryUtils.isEmptyFluidContainer(slotStack) || InventoryUtils.isFilledFluidContainer(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 0, size - 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < inventoryEnd) {
                    if (!this.moveItemStackTo(slotStack, inventoryEnd, hotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < hotbarEnd && !this.moveItemStackTo(slotStack, size, inventoryEnd, false)) {
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
