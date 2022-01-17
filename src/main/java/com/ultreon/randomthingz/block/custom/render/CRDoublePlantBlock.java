package com.ultreon.randomthingz.block.custom.render;

import com.ultreon.modlib.common.interfaces.IHasRenderType;
import net.minecraft.world.level.block.DoublePlantBlock;

/**
 * Door block with custom render type.
 *
 * @author Qboi123
 */
public abstract class CRDoublePlantBlock extends DoublePlantBlock implements IHasRenderType {
    public CRDoublePlantBlock(Properties builder) {
        super(builder);
    }
}
