package com.qtech.forgemod.client.groups;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Wood item group.
 * For miscellaneous wood items and blocks.
 *
 * @author Qboi123
 */
public class WoodItemGroup extends ItemGroup {
    public static final WoodItemGroup instance = new WoodItemGroup(ItemGroup.GROUPS.length, "qforgemod_wood");

    public WoodItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.OAK_PLANKS);
    }
}
