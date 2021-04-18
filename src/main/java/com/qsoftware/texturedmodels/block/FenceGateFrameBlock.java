package com.qsoftware.texturedmodels.block;

import com.qsoftware.texturedmodels.QTextureModels;
import com.qsoftware.texturedmodels.setup.Registration;
import com.qsoftware.texturedmodels.setup.config.BCModConfig;
import com.qsoftware.texturedmodels.tileentity.FrameBlockTile;
import com.qsoftware.texturedmodels.util.BlockAppearanceHelper;
import com.qsoftware.texturedmodels.util.BlockSavingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;

import static com.qsoftware.texturedmodels.util.BCBlockStateProperties.CONTAINS_BLOCK;
import static com.qsoftware.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;
import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

/**
 * Main class for frame fence gates - all important block info can be found here
 * Visit {@link FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.3 10/06/20
 */
public class FenceGateFrameBlock extends FenceGateBlock implements IWaterLoggable {
    public FenceGateFrameBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(OPEN, Boolean.FALSE).with(POWERED, Boolean.FALSE).with(IN_WALL, Boolean.FALSE).with(CONTAINS_BLOCK, false).with(LIGHT_LEVEL, 0).with(WATERLOGGED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, HORIZONTAL_FACING, OPEN, POWERED, IN_WALL, CONTAINS_BLOCK, LIGHT_LEVEL);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader dimension) {
        return new FrameBlockTile();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World dimension, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        ItemStack item = player.getHeldItem(hand);
        if (!dimension.isClientSided) {
            BlockAppearanceHelper.setLightLevel(item, state, dimension, pos, player, hand);
            BlockAppearanceHelper.setTexture(item, state, dimension, player, pos);
            BlockAppearanceHelper.setDesign(dimension, pos, player, item);
            BlockAppearanceHelper.setDesignTexture(dimension, pos, player, item);
            if ((state.get(CONTAINS_BLOCK) || !(item.getItem() instanceof BlockItem)) && !(Objects.requireNonNull(item.getItem().getRegistryName()).getNamespace().equals(QTextureModels.MOD_ID))) {
                if (state.get(OPEN)) {
                    state = state.with(OPEN, Boolean.FALSE);
                } else {
                    Direction direction = player.getHorizontalFacing();
                    if (state.get(HORIZONTAL_FACING) == direction.getOpposite()) {
                        state = state.with(HORIZONTAL_FACING, direction);
                    }
                    state = state.with(OPEN, Boolean.TRUE);
                }
                dimension.setBlockState(pos, state, 10);
                dimension.playEvent(player, state.get(OPEN) ? 1008 : 1014, pos, 0);
                //return ActionResultType.SUCCESS;
            } else {
                if (item.getItem() instanceof BlockItem) {
                    TileEntity tileEntity = dimension.getTileEntity(pos);
                    int count = player.getHeldItem(hand).getCount();
                    Block heldBlock = ((BlockItem) item.getItem()).getBlock();
                    if (tileEntity instanceof FrameBlockTile && !item.isEmpty() && BlockSavingHelper.isValidBlock(heldBlock) && !state.get(CONTAINS_BLOCK)) {
                        ((FrameBlockTile) tileEntity).clear();
                        BlockState handBlockState = ((BlockItem) item.getItem()).getBlock().getDefaultState();
                        ((FrameBlockTile) tileEntity).setMimic(handBlockState);
                        insertBlock(dimension, pos, state, handBlockState);
                        if (!player.isCreative())
                            player.getHeldItem(hand).setCount(count - 1);

                    }
                }
            }
            if (player.getHeldItem(hand).getItem() == Registration.HAMMER.get() || (!BCModConfig.HAMMER_NEEDED.get() && player.isSneaking())) {
                if (!player.isCreative())
                    this.dropContainedBlock(dimension, pos);
                state = state.with(CONTAINS_BLOCK, Boolean.FALSE);
                dimension.setBlockState(pos, state, 2);
            }
        }
        return ActionResultType.SUCCESS;
    }

    protected void dropContainedBlock(World dimensionIn, BlockPos pos) {
        if (!dimensionIn.isClientSided) {
            TileEntity tileentity = dimensionIn.getTileEntity(pos);
            if (tileentity instanceof FrameBlockTile) {
                FrameBlockTile frameTileEntity = (FrameBlockTile) tileentity;
                BlockState blockState = frameTileEntity.getMimic();
                if (!(blockState == null)) {
                    dimensionIn.playEvent(1010, pos, 0);
                    frameTileEntity.clear();
                    float f = 0.7F;
                    double d0 = (double) (dimensionIn.rand.nextFloat() * 0.7F) + (double) 0.15F;
                    double d1 = (double) (dimensionIn.rand.nextFloat() * 0.7F) + (double) 0.060000002F + 0.6D;
                    double d2 = (double) (dimensionIn.rand.nextFloat() * 0.7F) + (double) 0.15F;
                    ItemStack itemstack1 = new ItemStack(blockState.getBlock());
                    ItemEntity itementity = new ItemEntity(dimensionIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, itemstack1);
                    itementity.setDefaultPickupDelay();
                    dimensionIn.spawnEntity(itementity);
                    frameTileEntity.clear();
                }
            }
        }
    }

    public void insertBlock(IWorld dimensionIn, BlockPos pos, BlockState state, BlockState handBlock) {
        TileEntity tileentity = dimensionIn.getTileEntity(pos);
        if (tileentity instanceof FrameBlockTile) {
            FrameBlockTile frameTileEntity = (FrameBlockTile) tileentity;
            frameTileEntity.clear();
            frameTileEntity.setMimic(handBlock);
            dimensionIn.setBlockState(pos, state.with(CONTAINS_BLOCK, Boolean.TRUE), 2);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(BlockState state, World dimensionIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            dropContainedBlock(dimensionIn, pos);

            super.onReplaced(state, dimensionIn, pos, newState, isMoving);
        }
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader dimension, BlockPos pos) {
        if (state.get(LIGHT_LEVEL) > 15) {
            return 15;
        }
        return state.get(LIGHT_LEVEL);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld dimensionIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            dimensionIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(dimensionIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, dimensionIn, currentPos, facingPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        FluidState fluidstate = context.getDimension().getFluidState(blockpos);
        BlockState state = super.getStateForPlacement(context);
        if (fluidstate.getFluid() == Fluids.WATER) {
            return Objects.requireNonNull(state).with(WATERLOGGED, fluidstate.isSource());
        } else {
            return state;
        }
    }
}
//========SOLI DEO GLORIA========//