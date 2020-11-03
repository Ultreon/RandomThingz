package com.qsoftware.forgemod.groups;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

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
