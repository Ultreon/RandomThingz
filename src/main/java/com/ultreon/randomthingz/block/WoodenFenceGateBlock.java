package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.RequiresToolMat;
import com.ultreon.randomthingz.common.RequiresToolType;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import org.jetbrains.annotations.Nullable;

public class WoodenFenceGateBlock extends FenceGateBlock implements RequiresToolMat, RequiresToolType {
    public WoodenFenceGateBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable ToolRequirement getRequirement() {
        return null;
    }

    @Override
    public @Nullable ToolType getToolType() {
        return ToolType.AXE;
    }
}
