package com.ultreon.randomthingz.item.group;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Tools item group.
 * For generic tools.
 *
 * @author Qboi123
 */
public class ToolsItemGroup extends CreativeModeTab {
    public static final ToolsItemGroup instance = new ToolsItemGroup(CreativeModeTab.TABS.length, "randomthingz_tools");

    public ToolsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull
    ItemStack makeIcon() {
        return new ItemStack(Items.IRON_AXE);
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
