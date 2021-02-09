package com.qsoftware.forgemod.modules.blocks.blocks.custom.render;

import com.qsoftware.forgemod.common.interfaces.IHasRenderType;
import net.minecraft.block.DoorBlock;

/**
 * Door block with custom render type.
 *
 * @author Qboi123
 */
public abstract class CRDoorBlock extends DoorBlock implements IHasRenderType {
    public CRDoorBlock(Properties builder) {
        super(builder);
    }
}
