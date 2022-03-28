package com.ultreon.randomthingz.block.machines.electricfurnace;

import com.ultreon.modlib.block.entity.UmlItemStackHandler;
import com.ultreon.modlib.inventory.OutputSlot;
import com.ultreon.modlib.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block.machines.MachineBlockEntity;
import com.ultreon.randomthingz.block.machines.MachineContainer;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.init.ModMachineContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ElectricFurnaceContainer extends MachineContainer<ElectricFurnaceBlockEntity> {
    public ElectricFurnaceContainer(int id, Inventory inv) {
        this(id, inv, new UmlItemStackHandler(2 + MachineTier.STANDARD.getUpgradeSlots()), BlockPos.ZERO, new SimpleContainerData(MachineBlockEntity.FIELDS_COUNT));
    }

    public ElectricFurnaceContainer(int id, Inventory inv, UmlItemStackHandler stackHandler, BlockPos pos, ContainerData fieldsIn) {
        super(ModMachineContainers.electricFurnace, id, inv, stackHandler, pos, fieldsIn);

        this.addSlot(new SlotItemHandler(stackHandler, 0, 56, 35));
        this.addSlot(new OutputSlot(stackHandler, 1, 117, 35));

        InventoryUtils.createPlayerSlots(inv, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.STANDARD;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack output = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            output = stack.copy();

            final int size = 2;
            final int inventoryEnd = size + 27;
            final int hotbarEnd = inventoryEnd + 9;

            if (index == 1) {
                if (!this.moveItemStackTo(stack, size, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack, output);
            } else if (index != 0) {
                if (this.isSmeltingIngredient(stack)) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < inventoryEnd) {
                    if (!this.moveItemStackTo(stack, inventoryEnd, hotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < hotbarEnd && !this.moveItemStackTo(stack, size, inventoryEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, size, hotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == output.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return output;
    }

    private boolean isSmeltingIngredient(ItemStack stack) {
        // TODO
        return true;
    }
}
