package com.ultreon.randomthingz.block.machines.compressor;

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
import net.minecraftforge.items.SlotItemHandler;

public class CompressorContainer extends MachineContainer<CompressorBlockEntity> {
    public CompressorContainer(int id, Inventory inv) {
        this(id, inv, new UmlItemStackHandler(2 + MachineTier.STANDARD.getUpgradeSlots()), BlockPos.ZERO, new SimpleContainerData(MachineBlockEntity.FIELDS_COUNT));
    }

    public CompressorContainer(int id, Inventory inv, UmlItemStackHandler stackHandler, BlockPos pos, ContainerData fieldsIn) {
        super(ModMachineContainers.compressor, id, inv, stackHandler, pos, fieldsIn);

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
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            final int inventorySize = 2;
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index == 1) {
                if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 0) {
                if (this.isCompressingIngredient(itemstack1)) {
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

    private boolean isCompressingIngredient(ItemStack stack) {
        // TODO
        return true;
    }
}
