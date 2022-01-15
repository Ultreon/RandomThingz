package com.ultreon.randomthingz.item.block;

import com.ultreon.randomthingz.common.IDeprecated;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class DeprecatedBlockItem extends BlockItem {
    public DeprecatedBlockItem(Block blockIn, Properties builder) {
        super(blockIn, fixProps(builder, blockIn));
    }

    @SuppressWarnings("ConstantConditions")
    private static Properties fixProps(Properties builder, Block blockIn) {
        if (blockIn instanceof IDeprecated) {
            builder.tab(null);
        }
        return builder;
    }
}
