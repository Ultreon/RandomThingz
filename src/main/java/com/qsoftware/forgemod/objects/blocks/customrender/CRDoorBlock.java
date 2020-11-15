package com.qsoftware.forgemod.objects.blocks.customrender;

import com.qsoftware.forgemod.common.IHasRenderType;
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
