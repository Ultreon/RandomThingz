package com.ultreon.randomthingz.block.machines.batterybox;

import com.ultreon.modlib.block.entity.UmlItemStackHandler;
import com.ultreon.modlib.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block.machines.MachineBaseBlockEntity;
import com.ultreon.randomthingz.block.machines.BaseEnergyStorageContainer;
import com.ultreon.randomthingz.init.ModMachineContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.SlotItemHandler;

public class BatteryBoxContainer extends BaseEnergyStorageContainer<BatteryBoxBlockEntity> {
    public BatteryBoxContainer(int id, Inventory inv) {
        this(id, inv, new UmlItemStackHandler(6), BlockPos.ZERO, new SimpleContainerData(MachineBaseBlockEntity.FIELDS_COUNT));
    }

    public BatteryBoxContainer(int id, Inventory inv, UmlItemStackHandler stackHandler, BlockPos pos, ContainerData fieldsIn) {
        super(ModMachineContainers.batteryBox, id, fieldsIn);

        this.addSlot(new SlotItemHandler(stackHandler, 0, 71, 19));
        this.addSlot(new SlotItemHandler(stackHandler, 1, 71, 37));
        this.addSlot(new SlotItemHandler(stackHandler, 2, 71, 55));
        this.addSlot(new SlotItemHandler(stackHandler, 3, 89, 19));
        this.addSlot(new SlotItemHandler(stackHandler, 4, 89, 37));
        this.addSlot(new SlotItemHandler(stackHandler, 5, 89, 55));

        InventoryUtils.createPlayerSlots(inv, 8, 84).forEach(this::addSlot);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack stackCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            stackCopy = stack.copy();

            final int inventorySize = BatteryBoxBlockEntity.INVENTORY_SIZE;
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index >= inventorySize) {
                if (this.isEnergyContainer(stack)) {
                    if (!this.moveItemStackTo(stack, 0, inventorySize, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerInventoryEnd) {
                    if (!this.moveItemStackTo(stack, playerInventoryEnd, playerHotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerHotbarEnd && !this.moveItemStackTo(stack, inventorySize, playerInventoryEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, inventorySize, playerHotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == stackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return stackCopy;
    }

    private boolean isEnergyContainer(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY).isPresent();
    }
}
