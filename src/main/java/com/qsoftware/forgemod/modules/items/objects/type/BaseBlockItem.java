package com.qsoftware.forgemod.modules.items.objects.type;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

/**
 * Base block item class.
 *
 * @author Qboi123
 * @deprecated Use {@link BlockItem} instead.
 */
@Deprecated
public class BaseBlockItem extends BlockItem {
    public BaseBlockItem(ItemGroup group, Block block) {
        super(block, new Item.Properties().group(group));
    }
}
