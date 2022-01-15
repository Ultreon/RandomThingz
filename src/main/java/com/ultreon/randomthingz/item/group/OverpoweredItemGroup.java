package com.ultreon.randomthingz.item.group;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

/**
 * OP item group.
 *
 * @author Qboi123
 */
public class OverpoweredItemGroup extends CreativeModeTab {
    public static final OverpoweredItemGroup instance = new OverpoweredItemGroup(CreativeModeTab.TABS.length, "randomthingz_god");

    public OverpoweredItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull
    ItemStack makeIcon() {
        return new ItemStack(Blocks.COMMAND_BLOCK);
    }
}
