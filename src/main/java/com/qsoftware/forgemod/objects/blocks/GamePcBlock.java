package com.qsoftware.forgemod.objects.blocks;

import com.qsoftware.forgemod.objects.items.base.FaceableBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class GamePcBlock extends FaceableBlock {
    private static final VoxelShape SHAPE_E = VoxelShapes.create(-3d / 16, 0.25d / 16, 2d / 16, 18d / 16, 22.25d / 16, 13d / 16);
    private static final VoxelShape SHAPE_S = VoxelShapes.create(2d / 16, 0.25d / 16, -3d / 16, 13d / 16, 22.25d / 16, 18d / 16);
    private static final VoxelShape SHAPE_W = VoxelShapes.create(-3d / 16, 0.25d / 16, 2d / 16, 18d / 16, 22.25d / 16, 13d / 16);
    private static final VoxelShape SHAPE_N = VoxelShapes.create(2d / 16, 0.25d / 16, -3d / 16, 13d / 16, 22.25d / 16, 18d / 16);

    public GamePcBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
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
    public float getAmbientOcclusionLightValue(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos) {
        return 0.6f;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (context.getPos().equals(new BlockPos(0, 255, 0))) {
            return super.getStateForPlacement(context);
        } else {
            return context.getWorld().getBlockState(context.getPos());
        }
    }
}
