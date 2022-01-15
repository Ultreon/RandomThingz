package com.ultreon.randomthingz.block.custom.render;

import com.qsoftware.modlib.common.interfaces.IHasRenderType;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

/**
 * Door block with custom render type.
 *
 * @author Qboi123
 */
public abstract class CRSaplingBlock extends SaplingBlock implements IHasRenderType {
    public CRSaplingBlock(AbstractTreeGrower treeIn, Properties properties) {
        super(treeIn, properties);
    }
}
