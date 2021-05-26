package com.qtech.randomthingz.client.groups;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Shapes item group.
 *
 * @author Qboi123
 */
public class ShapesItemGroup extends ItemGroup {
    public static final ShapesItemGroup instance = new ShapesItemGroup(ItemGroup.GROUPS.length, "randomthingz_shapes");

    public ShapesItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.OAK_STAIRS);
    }
}
