package com.ultreon.randomthingz.item.block;

import com.ultreon.randomthingz.common.IDeprecated;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class DeprecatedBlockItem extends BlockItem {
    public DeprecatedBlockItem(Block blockIn, Properties builder) {
        super(blockIn, fixProps(builder, blockIn));
    }

    @SuppressWarnings("ConstantConditions")
    private static Properties fixProps(Properties builder, Block blockIn) {
        if (blockIn instanceof IDeprecated) {
            builder.group(null);
        }
        return builder;
    }
}
