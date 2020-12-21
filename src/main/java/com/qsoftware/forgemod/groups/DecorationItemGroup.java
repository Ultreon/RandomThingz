package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.common.QItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;

public class DecorationItemGroup extends QItemGroup {
    public DecorationItemGroup(String label) {
        super(label, new ItemStack(Blocks.BOOKSHELF));
    }
}
