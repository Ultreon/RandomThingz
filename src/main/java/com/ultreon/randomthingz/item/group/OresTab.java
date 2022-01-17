package com.ultreon.randomthingz.item.group;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

/**
 * Ore item group.
 *
 * @author Qboi123
 */
public class OresTab extends CreativeModeTab {
    public static final OresTab instance = new OresTab(CreativeModeTab.TABS.length, "randomthingz_ores");

    public OresTab(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull
    ItemStack makeIcon() {
        return new ItemStack(Blocks.DIAMOND_ORE);
    }
}
