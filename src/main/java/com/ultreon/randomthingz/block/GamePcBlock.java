package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.commons.IDeprecated;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Game PC block class.
 *
 * @author Qboi123
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("deprecation")
public class GamePcBlock extends DirectionalBlock implements IDeprecated {
    private static final VoxelShape SHAPE_E = VoxelShapes.create(-3d / 16, 0.25d / 16, 2d / 16, 18d / 16, 22.25d / 16, 13d / 16);
    private static final VoxelShape SHAPE_S = VoxelShapes.create(2d / 16, 0.25d / 16, -3d / 16, 13d / 16, 22.25d / 16, 18d / 16);
    private static final VoxelShape SHAPE_W = VoxelShapes.create(-3d / 16, 0.25d / 16, 2d / 16, 18d / 16, 22.25d / 16, 13d / 16);
    private static final VoxelShape SHAPE_N = VoxelShapes.create(2d / 16, 0.25d / 16, -3d / 16, 13d / 16, 22.25d / 16, 18d / 16);

    public GamePcBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull IBlockReader dimensionIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
        switch (state.get(FACING)) {
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }

    @Override
    public float getAmbientOcclusionLightValue(@NotNull BlockState state, @NotNull IBlockReader dimensionIn, @NotNull BlockPos pos) {
        return 0.6f;
    }
}
