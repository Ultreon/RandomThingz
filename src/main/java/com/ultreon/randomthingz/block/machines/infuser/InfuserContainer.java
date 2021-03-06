package com.ultreon.randomthingz.block.machines.infuser;

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

public class InfuserContainer extends BaseMachineContainer<InfuserBlockEntity> {
    public InfuserContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, new UmlItemStackHandler(4 + MachineTier.STANDARD.getUpgradeSlots()), BlockPos.ZERO, new SimpleContainerData(InfuserBlockEntity.FIELDS_COUNT));
    }

    public InfuserContainer(int id, Inventory inv, UmlItemStackHandler stackHandler, BlockPos pos, ContainerData fieldsIn) {
        super(ModMachineContainers.infuser, id, inv, stackHandler, pos, fieldsIn);

        this.addSlot(new SlotItemHandler(stackHandler, InfuserBlockEntity.SLOT_FLUID_CONTAINER_IN, 8, 16));
        this.addSlot(new OutputSlot(stackHandler, InfuserBlockEntity.SLOT_FLUID_CONTAINER_OUT, 8, 59));
        this.addSlot(new SlotItemHandler(stackHandler, InfuserBlockEntity.SLOT_ITEM_IN, 54, 35));
        this.addSlot(new OutputSlot(stackHandler, InfuserBlockEntity.SLOT_ITEM_OUT, 116, 35));

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
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            final int inventorySize = 2;
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index > 0 && index < 4) {
                if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 0) {
                if (this.isInfusingIngredient(itemstack1)) {
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

    private boolean isInfusingIngredient(ItemStack stack) {
        // TODO
        return true;
    }
}
