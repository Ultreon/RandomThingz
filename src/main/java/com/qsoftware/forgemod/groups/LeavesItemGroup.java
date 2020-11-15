package com.qsoftware.forgemod.groups;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Leaves item group.
 * Will be in the future merged with {@link NatureItemGroup}.
 *
 * @author Qboi123
 * @deprecated Use {@link Groups#NATURE} instead, on need of class: Use {@link NatureItemGroup}.
 */
@Deprecated
public class LeavesItemGroup extends ItemGroup {
    public static final LeavesItemGroup instance = new LeavesItemGroup(ItemGroup.GROUPS.length, "qforgemod_leaves");

    public LeavesItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.OAK_LEAVES);
    }
}
