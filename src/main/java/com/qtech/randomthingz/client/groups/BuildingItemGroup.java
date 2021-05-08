package com.qtech.randomthingz.client.groups;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.commons.BetterItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BuildingItemGroup extends BetterItemGroup {
    public BuildingItemGroup() {
        super(new ResourceLocation(RandomThingz.MOD_ID, "building"), new ItemStack(Blocks.BOOKSHELF));
    }
}
