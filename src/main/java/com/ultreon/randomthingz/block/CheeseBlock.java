package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.interfaces.Sliceable;
import com.ultreon.randomthingz.init.ModStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class CheeseBlock extends Block implements Sliceable {
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 4);
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.box(4.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.box(7.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.box(10.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.box(12.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
    };

    public CheeseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter dimensionIn, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(BITES)];
    }

    public InteractionResult use(BlockState state, Level dimensionIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (dimensionIn.isClientSide) {
            ItemStack itemstack = player.getItemInHand(handIn);
            if (this.eatSlice(dimensionIn, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return this.eatSlice(dimensionIn, pos, state, player);
    }

    private InteractionResult eatSlice(LevelAccessor dimension, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            player.awardStat(ModStats.EAT_CHEESE_SLICE);
            player.getFoodData().eat(2, 0.4F);
            int i = state.getValue(BITES);
            if (i < 4) {
                dimension.setBlock(pos, state.setValue(BITES, i + 1), 3);
            } else {
                dimension.removeBlock(pos, false);
            }

            return InteractionResult.SUCCESS;
        }
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor dimensionIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(dimensionIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, dimensionIn, currentPos, facingPos);
    }

    public boolean canSurvive(BlockState state, LevelReader dimensionIn, BlockPos pos) {
        return dimensionIn.getBlockState(pos.below()).getMaterial().isSolid();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    public int getAnalogOutputSignal(BlockState blockState, Level dimensionIn, BlockPos pos) {
        return (7 - blockState.getValue(BITES)) * 2;
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public boolean isPathfindable(BlockState state, BlockGetter dimensionIn, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public ItemStack onKnifeSlice(ItemStack stack) {
        return ItemStack.EMPTY;
    }
}
