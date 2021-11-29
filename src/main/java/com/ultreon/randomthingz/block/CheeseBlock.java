package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.modules.ui.ModStats;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CheeseBlock extends Block {
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 4);
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(4.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(7.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(10.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(12.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
    };

    public CheeseBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader dimensionIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(BITES)];
    }

    public ActionResultType onBlockActivated(BlockState state, World dimensionIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (dimensionIn.isClientSided) {
            ItemStack itemstack = player.getHeldItem(handIn);
            if (this.eatSlice(dimensionIn, pos, state, player).isSuccessOrConsume()) {
                return ActionResultType.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return ActionResultType.CONSUME;
            }
        }

        return this.eatSlice(dimensionIn, pos, state, player);
    }

    private ActionResultType eatSlice(IWorld dimension, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canEat(false)) {
            return ActionResultType.PASS;
        } else {
            player.addStat(ModStats.EAT_CHEESE_SLICE);
            player.getFoodStats().addStats(2, 0.4F);
            int i = state.get(BITES);
            if (i < 4) {
                dimension.setBlockState(pos, state.with(BITES, i + 1), 3);
            } else {
                dimension.deleteBlock(pos, false);
            }

            return ActionResultType.SUCCESS;
        }
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld dimensionIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.isValidPosition(dimensionIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, dimensionIn, currentPos, facingPos);
    }

    public boolean isValidPosition(BlockState state, IWorldReader dimensionIn, BlockPos pos) {
        return dimensionIn.getBlockState(pos.down()).getMaterial().isSolid();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    public int getComparatorInputOverride(BlockState blockState, World dimensionIn, BlockPos pos) {
        return (7 - blockState.get(BITES)) * 2;
    }

    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    public boolean allowsMovement(BlockState state, IBlockReader dimensionIn, BlockPos pos, PathType type) {
        return false;
    }
}
