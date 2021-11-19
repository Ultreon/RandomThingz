package com.ultreon.randomthingz.client.groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Ingots, nuggets and dusts item group.
 * Used for metal crafting items.
 *
 * @author Qboi123
 */
public class MetalCraftablesItemGroup extends ItemGroup {
    public static final MetalCraftablesItemGroup instance = new MetalCraftablesItemGroup(ItemGroup.GROUPS.length, "randomthingz_metal_craftables");

    public MetalCraftablesItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.IRON_INGOT);
    }
}
