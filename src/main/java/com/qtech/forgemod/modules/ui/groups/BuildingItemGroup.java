package com.qtech.forgemod.modules.ui.groups;

import com.qtech.forgemod.QForgeMod;
import com.qsoftware.modlib.common.BetterItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BuildingItemGroup extends BetterItemGroup {
    public BuildingItemGroup() {
        super(new ResourceLocation(QForgeMod.modId, "building"), new ItemStack(Blocks.BOOKSHELF));
    }
}
