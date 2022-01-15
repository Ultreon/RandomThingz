package com.ultreon.randomthingz.block.custom.render;

import com.qsoftware.modlib.common.interfaces.IHasRenderType;
import net.minecraft.world.level.block.TrapDoorBlock;

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
