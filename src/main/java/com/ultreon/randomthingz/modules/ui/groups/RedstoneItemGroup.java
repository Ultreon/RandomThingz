package com.ultreon.randomthingz.modules.ui.groups;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Redstone item group.
 *
 * @author Qboi123
 */
public class RedstoneItemGroup extends ItemGroup {
    public static final RedstoneItemGroup instance = new RedstoneItemGroup(ItemGroup.GROUPS.length, "randomthingz_redstone");

    public RedstoneItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull
    ItemStack createIcon() {
        return new ItemStack(Blocks.PISTON);
    }
}
