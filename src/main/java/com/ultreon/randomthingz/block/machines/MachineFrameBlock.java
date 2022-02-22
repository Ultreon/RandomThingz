package com.ultreon.randomthingz.block.machines;

import com.ultreon.randomthingz.common.RequiresToolMat;
import com.ultreon.randomthingz.common.RequiresToolType;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.StainedGlassBlock;

/**
 * Machine frame block. Currently this extends StainedGlassBlock to work around a Forge bug (#32)
 */
public class MachineFrameBlock extends StainedGlassBlock implements RequiresToolMat, RequiresToolType {
    public MachineFrameBlock(Properties properties) {
        super(DyeColor.WHITE, properties);
    }

    @Override
    public ToolRequirement getRequirement() {
        return ToolRequirement.WOOD;
    }

    @Override
    public ToolType getToolType() {
        return ToolType.PICKAXE;
    }
}
