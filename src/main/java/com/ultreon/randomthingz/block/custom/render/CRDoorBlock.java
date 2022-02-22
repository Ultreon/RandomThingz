package com.ultreon.randomthingz.block.custom.render;

import com.ultreon.modlib.common.interfaces.IHasRenderType;
import com.ultreon.randomthingz.block.door.DoorType;
import com.ultreon.randomthingz.common.RequiresToolMat;
import com.ultreon.randomthingz.common.RequiresToolType;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.world.level.block.DoorBlock;

/**
 * Door block with custom render type.
 *
 * @author Qboi123
 */
public abstract class CRDoorBlock extends DoorBlock implements IHasRenderType, RequiresToolMat, RequiresToolType {
    private final DoorType type;

    public CRDoorBlock(DoorType type, Properties builder) {
        super(switch (type) {
            case WOOD -> builder;
            case IRON -> builder.requiresCorrectToolForDrops();
        });
        this.type = type;
    }

    @Override
    public ToolRequirement getRequirement() {
        return switch (type) {
            case WOOD -> null;
            case IRON -> ToolRequirement.STONE;
        };
    }

    @Override
    public ToolType getToolType() {
        return switch (type) {
            case WOOD -> ToolType.AXE;
            case IRON -> ToolType.PICKAXE;
        };
    }
}
