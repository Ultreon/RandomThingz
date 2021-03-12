package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.BetterItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BuildingItemGroup extends BetterItemGroup {
    public BuildingItemGroup() {
        super(new ResourceLocation(QForgeMod.modId, "building"), new ItemStack(Blocks.BOOKSHELF));
    }
}
