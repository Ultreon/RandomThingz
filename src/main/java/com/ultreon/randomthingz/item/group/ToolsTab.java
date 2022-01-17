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
public class ToolsTab extends CreativeModeTab {
    public static final ToolsTab instance = new ToolsTab(CreativeModeTab.TABS.length, "randomthingz_tools");

    public ToolsTab(int index, String label) {
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
