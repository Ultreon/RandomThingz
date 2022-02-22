package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.RequiresToolMat;
import com.ultreon.randomthingz.common.RequiresToolType;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

public class ModOreBlock extends OreBlock implements RequiresToolMat, RequiresToolType {
    private ToolType toolType;
    private ToolRequirement toolRequirement;

    public ModOreBlock(BlockBehaviour.Properties properties, @Nullable ToolType toolType, @Nullable ToolRequirement toolRequirement) {
        super(properties);
    }

    public ModOreBlock(BlockBehaviour.Properties properties, UniformInt xpRange, @Nullable ToolType toolType, @Nullable ToolRequirement toolRequirement) {
        super(properties, xpRange);
        this.toolType = toolType;
        this.toolRequirement = toolRequirement;
    }

    @Override
    public ToolRequirement getRequirement() {
        return toolRequirement;
    }

    @Override
    public ToolType getToolType() {
        return toolType;
    }
}
