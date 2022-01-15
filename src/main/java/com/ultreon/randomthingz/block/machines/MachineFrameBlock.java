package com.ultreon.randomthingz.block.machines;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.StainedGlassBlock;

/**
 * Machine frame block. Currently this extends StainedGlassBlock to work around a Forge bug (#32)
 */
public class MachineFrameBlock extends StainedGlassBlock {
    public MachineFrameBlock(Properties properties) {
        super(DyeColor.WHITE, properties);
    }
}
