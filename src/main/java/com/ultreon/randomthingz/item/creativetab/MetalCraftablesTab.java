package com.ultreon.randomthingz.item.creativetab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Ingots, nuggets and dusts item group.
 * Used for metal crafting items.
 *
 * @author Qboi123
 */
public class MetalCraftablesTab extends CreativeModeTab {
    public static final MetalCraftablesTab instance = new MetalCraftablesTab(CreativeModeTab.TABS.length, "randomthingz_metal_craftables");

    public MetalCraftablesTab(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull
    ItemStack makeIcon() {
        return new ItemStack(Items.IRON_INGOT);
    }
}
