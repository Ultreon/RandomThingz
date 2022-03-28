package com.ultreon.randomthingz.block.machines.generator.coal;

import com.ultreon.modlib.block.entity.UmlItemStackHandler;
import com.ultreon.modlib.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block.machines.MachineBlockEntity;
import com.ultreon.randomthingz.block.machines.BaseMachineContainer;
import com.ultreon.randomthingz.init.ModMachineContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CoalGeneratorContainer extends BaseMachineContainer<CoalGeneratorBlockEntity> {
    public CoalGeneratorContainer(int id, Inventory inv) {
        this(id, inv, new UmlItemStackHandler(1), BlockPos.ZERO, new SimpleContainerData(MachineBlockEntity.FIELDS_COUNT));
    }

    public CoalGeneratorContainer(int id, Inventory inv, UmlItemStackHandler itemHandler, BlockPos pos, ContainerData fieldsIn) {
        super(ModMachineContainers.coalGenerator, id, inv, itemHandler, pos, fieldsIn);

        this.addSlot(new SlotItemHandler(itemHandler, 0, 80, 33));

        InventoryUtils.createPlayerSlots(inv, 8, 84).forEach(this::addSlot);

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
        ItemStack output = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            output = slotItem.copy();

            final int size = 1;
            final int invEnd = size + 27;
            final int hotbarEnd = invEnd + 9;

            if (index != 0) {
                if (this.isFuel(slotItem)) {
                    if (!this.moveItemStackTo(slotItem, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < invEnd) {
                    if (!this.moveItemStackTo(slotItem, invEnd, hotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < hotbarEnd && !this.moveItemStackTo(slotItem, size, invEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotItem, size, hotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotItem.getCount() == output.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotItem);
        }

        return output;
    }

    private boolean isFuel(ItemStack stack) {
        return CoalGeneratorBlockEntity.isFuel(stack);
    }
}
