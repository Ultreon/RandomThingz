package com.qtech.randomthingz.item;

import com.qtech.randomthingz.item.energy.EnergyStoringItem;
import com.qtech.randomthingz.modules.ui.ModItemGroups;
import net.minecraft.item.Rarity;

public class BatteryItem extends EnergyStoringItem {
    private static final int MAX_ENERGY = 500_000;
    private static final int MAX_TRANSFER = 500;

    public BatteryItem() {
        super(new Properties().group(ModItemGroups.MACHINES).maxStackSize(1).rarity(Rarity.UNCOMMON), MAX_ENERGY, MAX_TRANSFER);
    }
}
