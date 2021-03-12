package com.qsoftware.forgemod.modules.ui.groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Bows & arrows item group..
 *
 * @author Qboi123
 */
public class FletchingItemGroup extends ItemGroup {
    public static final FletchingItemGroup instance = new FletchingItemGroup(ItemGroup.GROUPS.length, "qforgemod_fletching");

    public FletchingItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.BOW);
    }
}
