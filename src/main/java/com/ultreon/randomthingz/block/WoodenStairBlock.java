package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.RequiresToolMat;
import com.ultreon.randomthingz.common.RequiresToolType;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class WoodenStairBlock extends StairBlock implements RequiresToolMat, RequiresToolType {
    public WoodenStairBlock(Supplier<BlockState> state, Properties properties) {
        super(state, properties);
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
