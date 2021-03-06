package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.init.ModCreativeTabs;
import com.ultreon.randomthingz.item.energy.EnergyStoringItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class BatteryItem extends EnergyStoringItem {
    private static final int MAX_ENERGY = 500_000;
    private static final int MAX_TRANSFER = 500;

    public BatteryItem() {
        super(new Item.Properties().tab(ModCreativeTabs.MACHINES).stacksTo(1).rarity(Rarity.UNCOMMON), MAX_ENERGY, MAX_TRANSFER);
    }
}
