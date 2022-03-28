package com.ultreon.randomthingz.block.machines.generator.lava;

import com.ultreon.modlib.block.entity.UmlItemStackHandler;
import com.ultreon.modlib.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block.machines.generator.AbstractFluidGeneratorContainer;
import com.ultreon.randomthingz.block.machines.generator.FluidFuelGeneratorBlockEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.init.ModMachineContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class LavaGeneratorContainer extends AbstractFluidGeneratorContainer<LavaGeneratorBlockEntity> {
    public LavaGeneratorContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, new UmlItemStackHandler(2 + MachineTier.STANDARD.getUpgradeSlots()), BlockPos.ZERO, new SimpleContainerData(FluidFuelGeneratorBlockEntity.FIELDS_COUNT));
    }

    public LavaGeneratorContainer(int id, Inventory inv, UmlItemStackHandler itemHandler, BlockPos pos, ContainerData fieldsIn) {
        super(ModMachineContainers.lavaGenerator, id, inv, itemHandler, pos, fieldsIn);

        this.addSlot(new SlotItemHandler(itemHandler, 0, 80, 16));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 80, 59));

        InventoryUtils.createPlayerSlots(inv, 8, 84).forEach(this::addSlot);

        if (itemHandler.getSlots() > 2) {
            this.addUpgradeSlots();
        }
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.STANDARD;
    }

    @Override
    protected boolean isFuel(ItemStack stack) {
        return stack.getItem() instanceof BucketItem && ((BucketItem) stack.getItem()).getFluid().is(FluidTags.LAVA);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            final int inventorySize = 1;
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
