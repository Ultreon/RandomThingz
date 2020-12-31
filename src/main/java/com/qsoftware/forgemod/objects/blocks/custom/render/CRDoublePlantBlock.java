package com.qsoftware.forgemod.objects.blocks.custom.render;

import com.qsoftware.forgemod.common.interfaces.IHasRenderType;
import net.minecraft.block.DoublePlantBlock;

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
