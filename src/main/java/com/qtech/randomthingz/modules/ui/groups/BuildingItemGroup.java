package com.qtech.randomthingz.modules.ui.groups;

import com.qsoftware.modlib.common.BetterItemGroup;
import com.qtech.randomthingz.RandomThingz;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BuildingItemGroup extends BetterItemGroup {
    public BuildingItemGroup() {
        super(new ResourceLocation(RandomThingz.MOD_ID, "building"), new ItemStack(Blocks.BOOKSHELF));
    }
}
