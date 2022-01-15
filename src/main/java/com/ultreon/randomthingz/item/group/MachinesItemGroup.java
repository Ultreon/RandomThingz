package com.ultreon.randomthingz.item.group;

import com.ultreon.randomthingz.block._common.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Machines item group.
 * Used for blocks that have tile entities or items that have an GUI.
 *
 * @author Qboi123
 */
public class MachinesItemGroup extends CreativeModeTab {
    public static final MachinesItemGroup instance = new MachinesItemGroup(CreativeModeTab.TABS.length, "randomthingz_machines");

    public MachinesItemGroup(int index, String label) {
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
