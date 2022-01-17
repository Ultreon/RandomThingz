package com.ultreon.randomthingz.block.custom.render;

import com.ultreon.modlib.common.interfaces.IHasRenderType;
import net.minecraft.world.level.block.DoorBlock;

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
