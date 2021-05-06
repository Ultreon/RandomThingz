package com.qtech.forgemod.block.custom.render;

import com.qsoftware.modlib.common.interfaces.IHasRenderType;
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
