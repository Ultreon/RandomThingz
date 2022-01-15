package com.ultreon.randomthingz.block.machines.generator.lava;

import com.qsoftware.modlib.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block.machines.generator.AbstractFluidFuelGeneratorTileEntity;
import com.ultreon.randomthingz.block.machines.generator.AbstractFluidGeneratorContainer;
import com.ultreon.randomthingz.init.ModMachineContainers;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;

public class LavaGeneratorContainer extends AbstractFluidGeneratorContainer<LavaGeneratorTileEntity> {
    public LavaGeneratorContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, new LavaGeneratorTileEntity(), new SimpleContainerData(AbstractFluidFuelGeneratorTileEntity.FIELDS_COUNT));
    }

    public LavaGeneratorContainer(int id, Inventory playerInventory, LavaGeneratorTileEntity tileEntity, ContainerData fieldsIn) {
        super(ModMachineContainers.lavaGenerator, id, tileEntity, fieldsIn);

        this.addSlot(new Slot(tileEntity, 0, 80, 16));
        this.addSlot(new Slot(tileEntity, 1, 80, 59));

        InventoryUtils.createPlayerSlots(playerInventory, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
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
