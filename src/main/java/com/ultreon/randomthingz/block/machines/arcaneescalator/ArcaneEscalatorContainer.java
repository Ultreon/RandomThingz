package com.ultreon.randomthingz.block.machines.arcaneescalator;

import com.ultreon.modlib.silentlib.inventory.SlotOutputOnly;
import com.ultreon.modlib.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block._common.MachineType;
import com.ultreon.randomthingz.block.machines.AbstractMachineBlockEntity;
import com.ultreon.randomthingz.block.machines.AbstractMachineContainer;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("ConstantConditions")
public class ArcaneEscalatorContainer extends AbstractMachineContainer<ArcaneEscalatorBlockEntity> {
    public ArcaneEscalatorContainer(int id, Inventory playerInventory, MachineTier tier) {
        this(id, playerInventory, MachineType.ARCANE_ESCALATOR.create(tier), new SimpleContainerData(AbstractMachineBlockEntity.FIELDS_COUNT));
    }

    protected ArcaneEscalatorContainer(int id, Inventory playerInventory, ArcaneEscalatorBlockEntity tileEntityIn, ContainerData fieldsIn) {
        super(MachineType.ARCANE_ESCALATOR.getContainerType(tileEntityIn.getMachineTier()), id, tileEntityIn, fieldsIn);

        for (int i = 0; i < ArcaneEscalatorBlockEntity.INPUT_SLOT_COUNT; ++i) {
            this.addSlot(new Slot(this.tileEntity, i, 17 + 18 * i, 35));
        }
        this.addSlot(new SlotOutputOnly(this.tileEntity, ArcaneEscalatorBlockEntity.INPUT_SLOT_COUNT, 126, 35));

        InventoryUtils.createPlayerSlots(playerInventory, 8, 84).forEach(this::addSlot);
        this.addUpgradeSlots();
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack output = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemInSlot = slot.getItem();
            output = itemInSlot.copy();

            final int inventorySize = this.tileEntity.getContainerSize();
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index == inventorySize - 1) {
                if (!this.moveItemStackTo(itemInSlot, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemInSlot, output);
            } else if (index >= inventorySize) {
                if (this.isArcaneEscalatingIngredient(itemInSlot)) {
                    if (!this.moveItemStackTo(itemInSlot, 0, inventorySize - 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerInventoryEnd) {
                    if (!this.moveItemStackTo(itemInSlot, playerInventoryEnd, playerHotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerHotbarEnd && !this.moveItemStackTo(itemInSlot, inventorySize, playerInventoryEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemInSlot, inventorySize, playerHotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (itemInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemInSlot.getCount() == output.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemInSlot);
        }

        return output;
    }

    private boolean isArcaneEscalatingIngredient(ItemStack stack) {
        // TODO
        return true;
    }
}
