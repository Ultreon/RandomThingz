package com.qsoftware.forgemod.groups;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

/**
 * Ore item group.
 *
 * @author Qboi123
 */
public class OresItemGroup extends ItemGroup {
    public static final OresItemGroup instance = new OresItemGroup(ItemGroup.GROUPS.length, "qforgemod_ores");

    public OresItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.DIAMOND_ORE);
    }
}
