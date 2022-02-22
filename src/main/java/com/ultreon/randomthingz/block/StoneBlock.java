package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.RequiresToolMat;
import com.ultreon.randomthingz.common.RequiresToolType;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class StoneBlock extends Block implements RequiresToolMat, RequiresToolType {
    public StoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable ToolRequirement getRequirement() {
        return ToolRequirement.WOOD;
    }

    @Override
    public @Nullable ToolType getToolType() {
        return ToolType.PICKAXE;
    }
}
