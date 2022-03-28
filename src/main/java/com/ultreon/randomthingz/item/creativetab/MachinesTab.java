package com.ultreon.randomthingz.item.creativetab;

import com.ultreon.randomthingz.init.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Machines item group.
 * Used for blocks that have tile entities or items that have an GUI.
 *
 * @author Qboi123
 */
public class MachinesTab extends CreativeModeTab {
    public static final MachinesTab instance = new MachinesTab(CreativeModeTab.TABS.length, "randomthingz_machines");

    public MachinesTab(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull
    ItemStack makeIcon() {
        return new ItemStack(ModBlocks.COAL_GENERATOR.get());
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
