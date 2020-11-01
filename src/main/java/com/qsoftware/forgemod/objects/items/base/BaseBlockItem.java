package com.qsoftware.forgemod.objects.items.base;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BaseBlockItem extends BlockItem {
    public BaseBlockItem(String name, ItemGroup group, Block block) {
        super(block, new Item.Properties().group(group));
        this.setRegistryName(name);
    }
}
