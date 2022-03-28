package com.ultreon.randomthingz.inventory.slot;

import com.ultreon.randomthingz.item.MachineUpgradeItem;
import com.ultreon.randomthingz.util.Constants;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MachineUpgradeSlot extends SlotItemHandler {
    public MachineUpgradeSlot(IItemHandler slots, int index, int xPosition, int yPosition) {
        super(slots, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof MachineUpgradeItem;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return Constants.UPGRADES_PER_SLOT;
    }
}
