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
public class OverpoweredTab extends CreativeModeTab {
    public static final OverpoweredTab instance = new OverpoweredTab(CreativeModeTab.TABS.length, "randomthingz_god");

    public OverpoweredTab(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull
    ItemStack makeIcon() {
        return new ItemStack(Blocks.COMMAND_BLOCK);
    }
}
