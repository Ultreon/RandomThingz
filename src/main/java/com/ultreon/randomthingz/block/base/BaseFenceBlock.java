package com.ultreon.randomthingz.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;

/**
 * Base fence block class.
 *
 * @author Qboi123
 * @deprecated Use {@linkplain FenceBlock} instead.
 */
@Deprecated
public class BaseFenceBlock extends FenceBlock {
    public BaseFenceBlock(Block.Properties properties) {
        super(properties);
    }
}
