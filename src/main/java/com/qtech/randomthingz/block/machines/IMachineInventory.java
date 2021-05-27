package com.qtech.randomthingz.block.machines;

import net.minecraft.inventory.IInventory;

public interface IMachineInventory extends IInventory {
    int getInputSlotCount();
}
