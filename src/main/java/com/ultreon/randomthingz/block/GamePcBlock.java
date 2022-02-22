package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.IDeprecated;
import com.ultreon.randomthingz.common.RequiresToolMat;
import com.ultreon.randomthingz.common.RequiresToolType;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Game PC block class.
 *
 * @author Qboi123
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("deprecation")
public class GamePcBlock extends DirectionalBlock implements IDeprecated, RequiresToolMat, RequiresToolType {
    private static final VoxelShape SHAPE_E = Shapes.box(-3d / 16, 0.25d / 16, 2d / 16, 18d / 16, 22.25d / 16, 13d / 16);
    private static final VoxelShape SHAPE_S = Shapes.box(2d / 16, 0.25d / 16, -3d / 16, 13d / 16, 22.25d / 16, 18d / 16);
    private static final VoxelShape SHAPE_W = Shapes.box(-3d / 16, 0.25d / 16, 2d / 16, 18d / 16, 22.25d / 16, 13d / 16);
    private static final VoxelShape SHAPE_N = Shapes.box(2d / 16, 0.25d / 16, -3d / 16, 13d / 16, 22.25d / 16, 18d / 16);

    public GamePcBlock() {
        super(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(4f).sound(SoundType.METAL));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter dimensionIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case EAST -> SHAPE_E;
            case SOUTH -> SHAPE_S;
            case WEST -> SHAPE_W;
            default -> SHAPE_N;
        };
    }

    @Override
    public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter dimensionIn, @NotNull BlockPos pos) {
        return .6f;
    }

    @Override
    public @Nullable ToolRequirement getRequirement() {
        return ToolRequirement.STONE;
    }

    @Override
    public @Nullable ToolType getToolType() {
        return ToolType.PICKAXE;
    }
}
