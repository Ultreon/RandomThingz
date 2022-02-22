package com.ultreon.randomthingz.block.custom;

import com.ultreon.randomthingz.block.ButtonType;
import com.ultreon.randomthingz.common.RequiresToolMat;
import com.ultreon.randomthingz.common.RequiresToolType;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.world.level.block.StoneButtonBlock;

public class CustomButtonBlock extends StoneButtonBlock implements RequiresToolMat, RequiresToolType {
    private final ButtonType type;
    private final int activeDuration;

    public CustomButtonBlock(ButtonType type, Properties properties, int activeDuration) {
        super(switch (type) {
            case WOODEN -> properties;
            case ROCK_OR_METAL -> properties.requiresCorrectToolForDrops();
        });
        this.type = type;
        this.activeDuration = activeDuration;
    }

    @Override
    public int getPressDuration() {
        return activeDuration;
    }

    @Override
    public ToolRequirement getRequirement() {
        return switch (type) {
            case WOODEN -> null;
            case ROCK_OR_METAL -> ToolRequirement.STONE;
        };
    }

    @Override
    public ToolType getToolType() {
        return switch (type) {
            case WOODEN -> ToolType.AXE;
            case ROCK_OR_METAL -> ToolType.PICKAXE;
        };
    }
}
