package com.qsoftware.forgemod.objects.blocks.custom.render;

import com.qsoftware.forgemod.common.interfaces.IHasRenderType;
import net.minecraft.block.TrapDoorBlock;

/**
 * Door block with custom render type.
 *
 * @author Qboi123
 */
public abstract class CRTrapDoorBlock extends TrapDoorBlock implements IHasRenderType {
    public CRTrapDoorBlock(Properties builder) {
        super(builder);
    }
}
