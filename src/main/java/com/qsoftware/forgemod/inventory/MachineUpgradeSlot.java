package com.qsoftware.forgemod.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import com.qsoftware.forgemod.objects.item.MachineUpgradeItem;
import com.qsoftware.forgemod.util.Constants;

public class MachineUpgradeSlot extends Slot {
    public MachineUpgradeSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof MachineUpgradeItem;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return Constants.UPGRADES_PER_SLOT;
    }
}