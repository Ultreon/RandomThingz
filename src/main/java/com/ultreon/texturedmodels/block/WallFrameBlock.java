package com.ultreon.texturedmodels.block;

import com.ultreon.texturedmodels.QTextureModels;
import com.ultreon.texturedmodels.setup.Registration;
import com.ultreon.texturedmodels.setup.config.BCModConfig;
import com.ultreon.texturedmodels.tileentity.FrameBlockTile;
import com.ultreon.texturedmodels.util.BCBlockStateProperties;
import com.ultreon.texturedmodels.util.BlockAppearanceHelper;
import com.ultreon.texturedmodels.util.BlockSavingHelper;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * Main class for frame walls - all important block info can be found here
 * Visit {@linkplain FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.3 10/06/20
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
//TODO make similar to vanilla walls
public class WallFrameBlock extends CrossCollisionBlock {
    public static final BooleanProperty CONTAINS_BLOCK = BCBlockStateProperties.CONTAINS_BLOCK;
    public static final IntegerProperty LIGHT_LEVEL = BCBlockStateProperties.LIGHT_LEVEL;
    private final VoxelShape[] renderShapes;

    public WallFrameBlock(Properties properties) {
        super(4.0F, 3.0F, 16.0F, 14.0F, 24.0F, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE).setValue(CONTAINS_BLOCK, Boolean.FALSE).setValue(LIGHT_LEVEL, 0));
        this.renderShapes = this.makeShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED, CONTAINS_BLOCK, LIGHT_LEVEL);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter dimensionIn, BlockPos pos) {
        return this.renderShapes[this.getAABBIndex(state)];
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter dimension) {
        return new FrameBlockTile();
    }

    @Override
    public InteractionResult use(BlockState state, Level dimension, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        ItemStack item = player.getItemInHand(hand);
        if (!dimension.isClientSide) {
            BlockAppearanceHelper.setLightLevel(item, state, dimension, pos, player, hand);
            BlockAppearanceHelper.setTexture(item, state, dimension, player, pos);
            BlockAppearanceHelper.setDesign(dimension, pos, player, item);
            BlockAppearanceHelper.setDesignTexture(dimension, pos, player, item);
            BlockAppearanceHelper.setGlassColor(dimension, pos, player, hand);
            BlockAppearanceHelper.setOverlay(dimension, pos, player, item);
            if (item.getItem() instanceof BlockItem) {
                if (state.getValue(BCBlockStateProperties.CONTAINS_BLOCK) || Objects.requireNonNull(item.getItem().getRegistryName()).getNamespace().equals(QTextureModels.MOD_ID)) {
                    return InteractionResult.PASS;
                }
                BlockEntity tileEntity = dimension.getBlockEntity(pos);
                int count = player.getItemInHand(hand).getCount();
                Block heldBlock = ((BlockItem) item.getItem()).getBlock();
                if (tileEntity instanceof FrameBlockTile && !item.isEmpty() && BlockSavingHelper.isValidBlock(heldBlock) && !state.getValue(CONTAINS_BLOCK)) {
                    BlockState handBlockState = ((BlockItem) item.getItem()).getBlock().defaultBlockState();
                    insertBlock(dimension, pos, state, handBlockState);
                    if (!player.isCreative())
                        player.getItemInHand(hand).setCount(count - 1);

                    return InteractionResult.SUCCESS;
                }
            }
            if (item.getItem() == Items.LEAD) {
                return LeadItem.bindPlayerMobs(player, dimension, pos);
            }
            if (player.getItemInHand(hand).getItem() == Registration.HAMMER.get() || (!BCModConfig.HAMMER_NEEDED.get() && player.isShiftKeyDown())) {
                if (!player.isCreative())
                    this.dropContainedBlock(dimension, pos);
                state = state.setValue(CONTAINS_BLOCK, Boolean.FALSE);
                dimension.setBlock(pos, state, 2);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    protected void dropContainedBlock(Level dimensionIn, BlockPos pos) {
        if (!dimensionIn.isClientSide) {
            BlockEntity tileentity = dimensionIn.getBlockEntity(pos);
            if (tileentity instanceof FrameBlockTile) {
                FrameBlockTile frameTileEntity = (FrameBlockTile) tileentity;
                BlockState blockState = frameTileEntity.getMimic();
                if (!(blockState == null)) {
                    dimensionIn.levelEvent(1010, pos, 0);
                    frameTileEntity.clear();
                    float f = 0.7F;
                    double d0 = (double) (dimensionIn.random.nextFloat() * 0.7F) + (double) 0.15F;
                    double d1 = (double) (dimensionIn.random.nextFloat() * 0.7F) + (double) 0.060000002F + 0.6D;
                    double d2 = (double) (dimensionIn.random.nextFloat() * 0.7F) + (double) 0.15F;
                    ItemStack itemstack1 = new ItemStack(blockState.getBlock());
                    ItemEntity itementity = new ItemEntity(dimensionIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, itemstack1);
                    itementity.setDefaultPickUpDelay();
                    dimensionIn.addFreshEntity(itementity);
                    frameTileEntity.clear();
                }
            }
        }
    }

    public void insertBlock(LevelAccessor dimensionIn, BlockPos pos, BlockState state, BlockState handBlock) {
        BlockEntity tileentity = dimensionIn.getBlockEntity(pos);
        if (tileentity instanceof FrameBlockTile) {
            FrameBlockTile frameTileEntity = (FrameBlockTile) tileentity;
            frameTileEntity.clear();
            frameTileEntity.setMimic(handBlock);
            dimensionIn.setBlock(pos, state.setValue(CONTAINS_BLOCK, Boolean.TRUE), 2);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level dimensionIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            dropContainedBlock(dimensionIn, pos);

            super.onRemove(state, dimensionIn, pos, newState, isMoving);
        }
    }

    @Override
    public int getLightValue(BlockState state, BlockGetter dimension, BlockPos pos) {
        if (state.getValue(LIGHT_LEVEL) > 15) {
            return 15;
        }
        return state.getValue(LIGHT_LEVEL);
    }

    //Following methods copied from vanilla FenceBlock
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return this.getShape(state, reader, pos, context);
    }

    public boolean isPathfindable(BlockState state, BlockGetter dimensionIn, BlockPos pos, PathComputationType type) {
        return false;
    }

    public boolean canConnect(BlockState state, boolean isSideSolid, Direction direction) {
        Block block = state.getBlock();
        boolean flag = this.isSameFence(block);
        boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
        return !isExceptionForConnection(block) && isSideSolid || flag || flag1;
    }

    private boolean isSameFence(Block block) {
        return block.is(BlockTags.FENCES) && block.is(BlockTags.WOODEN_FENCES) == this.defaultBlockState().is(BlockTags.WOODEN_FENCES);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockState blockstate = iblockreader.getBlockState(blockpos1);
        BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
        BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
        BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
        return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(NORTH, this.canConnect(blockstate, blockstate.isFaceSturdy(iblockreader, blockpos1, Direction.SOUTH), Direction.SOUTH)).setValue(EAST, this.canConnect(blockstate1, blockstate1.isFaceSturdy(iblockreader, blockpos2, Direction.WEST), Direction.WEST)).setValue(SOUTH, this.canConnect(blockstate2, blockstate2.isFaceSturdy(iblockreader, blockpos3, Direction.NORTH), Direction.NORTH)).setValue(WEST, this.canConnect(blockstate3, blockstate3.isFaceSturdy(iblockreader, blockpos4, Direction.EAST), Direction.EAST)).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor dimensionIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            dimensionIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(dimensionIn));
        }

        return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), this.canConnect(facingState, facingState.isFaceSturdy(dimensionIn, facingPos, facing.getOpposite()), facing.getOpposite())) : super.updateShape(stateIn, facing, facingState, dimensionIn, currentPos, facingPos);
    }
}
//========SOLI DEO GLORIA========//
