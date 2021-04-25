package com.qtech.forgemod.modules.tiles.blocks.machines;

import net.minecraft.block.StainedGlassBlock;
import net.minecraft.item.DyeColor;

/**
 * Machine frame block. Currently this extends StainedGlassBlock to work around a Forge bug (#32)
 */
public class MachineFrameBlock extends StainedGlassBlock {
    public MachineFrameBlock(Properties properties) {
        super(DyeColor.WHITE, properties);
    }
}
