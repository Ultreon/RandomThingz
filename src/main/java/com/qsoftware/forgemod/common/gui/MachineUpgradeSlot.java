package com.qsoftware.forgemod.common.gui;

import com.qsoftware.forgemod.modules.items.objects.upgrades.MachineUpgradeItem;
import com.qsoftware.forgemod.util.Constants;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

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
