package com.ultreon.randomthingz.item.block;

import com.ultreon.randomthingz.commons.IDeprecated;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ModBlockItem extends BlockItem {
    public ModBlockItem(Block blockIn, Properties builder) {
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
