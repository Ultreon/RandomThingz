package com.ultreon.randomthingz.item.group;

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
public class MetalCraftablesItemGroup extends CreativeModeTab {
    public static final MetalCraftablesItemGroup instance = new MetalCraftablesItemGroup(CreativeModeTab.TABS.length, "randomthingz_metal_craftables");

    public MetalCraftablesItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull
    ItemStack makeIcon() {
        return new ItemStack(Items.IRON_INGOT);
    }
}
