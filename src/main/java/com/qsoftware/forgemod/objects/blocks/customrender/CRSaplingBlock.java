package com.qsoftware.forgemod.objects.blocks.customrender;

import com.qsoftware.forgemod.common.IHasRenderType;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;

/**
 * Door block with custom render type.
 *
 * @author Qboi123
 */
public abstract class CRSaplingBlock extends SaplingBlock implements IHasRenderType {
    public CRSaplingBlock(Tree treeIn, Properties properties) {
        super(treeIn, properties);
    }
}
