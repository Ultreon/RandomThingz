package com.qtech.forgemod.client.groups;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.commons.BetterItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BuildingItemGroup extends BetterItemGroup {
    public BuildingItemGroup() {
        super(new ResourceLocation(QForgeMod.MOD_ID, "building"), new ItemStack(Blocks.BOOKSHELF));
    }
}
