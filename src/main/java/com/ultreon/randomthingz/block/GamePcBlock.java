package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.IDeprecated;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
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
    private static final VoxelShape SHAPE_E = Shapes.box(-3d / 16, 0.25d / 16, 2d / 16, 18d / 16, 22.25d / 16, 13d / 16);
    private static final VoxelShape SHAPE_S = Shapes.box(2d / 16, 0.25d / 16, -3d / 16, 13d / 16, 22.25d / 16, 18d / 16);
    private static final VoxelShape SHAPE_W = Shapes.box(-3d / 16, 0.25d / 16, 2d / 16, 18d / 16, 22.25d / 16, 13d / 16);
    private static final VoxelShape SHAPE_N = Shapes.box(2d / 16, 0.25d / 16, -3d / 16, 13d / 16, 22.25d / 16, 18d / 16);

    public GamePcBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter dimensionIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        switch (state.getValue(FACING)) {
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
    public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter dimensionIn, @NotNull BlockPos pos) {
        return 0.6f;
    }
}
