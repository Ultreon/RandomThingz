package com.qsoftware.texturedmodels.block;

import com.qsoftware.texturedmodels.QTextureModels;
import com.qsoftware.texturedmodels.setup.Registration;
import com.qsoftware.texturedmodels.setup.config.BCModConfig;
import com.qsoftware.texturedmodels.tileentity.ChestFrameTileEntity;
import com.qsoftware.texturedmodels.util.BlockAppearanceHelper;
import com.qsoftware.texturedmodels.util.BlockSavingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
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
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Objects;

import static com.qsoftware.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

/**
 * Main class for frame chests - all important block info can be found here
 * Visit {@link FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.4 10/06/20
 */
public class ChestFrameBlock extends FrameBlock implements IWaterLoggable {
    private static final VoxelShape INNER_CUBE = Block.createCuboidShape(2.0, 2.0, 2.0, 14.0, 14.0, 14.0);
    private static final VoxelShape BOTTOM_NORTH = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 2.0);
    private static final VoxelShape BOTTOM_EAST = Block.createCuboidShape(14.0, 0.0, 2.0, 16.0, 2.0, 14.0);
    private static final VoxelShape BOTTOM_SOUTH = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 2.0, 16.0);
    private static final VoxelShape BOTTOM_WEST = Block.createCuboidShape(0.0, 0.0, 2.0, 2.0, 2.0, 14.0);
    private static final VoxelShape TOP_NORTH = Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 2.0);
    private static final VoxelShape TOP_EAST = Block.createCuboidShape(14.0, 14.0, 2.0, 16.0, 16.0, 14.0);
    private static final VoxelShape TOP_SOUTH = Block.createCuboidShape(0.0, 14.0, 14.0, 16.0, 16.0, 16.0);
    private static final VoxelShape TOP_WEST = Block.createCuboidShape(0.0, 14.0, 2.0, 2.0, 16.0, 14.0);
    private static final VoxelShape NW_PILLAR = Block.createCuboidShape(0.0, 2.0, 0.0, 2.0, 14.0, 2.0);
    private static final VoxelShape SW_PILLAR = Block.createCuboidShape(0.0, 2.0, 14.0, 2.0, 14.0, 16.0);
    private static final VoxelShape NE_PILLAR = Block.createCuboidShape(14.0, 2.0, 0.0, 16.0, 14.0, 2.0);
    private static final VoxelShape SE_PILLAR = Block.createCuboidShape(14.0, 2.0, 14.0, 16.0, 14.0, 16.0);
    private static final VoxelShape CHEST = VoxelShapes.or(INNER_CUBE, BOTTOM_EAST, BOTTOM_SOUTH, BOTTOM_WEST, BOTTOM_NORTH, TOP_EAST, TOP_SOUTH, TOP_WEST, TOP_NORTH, NW_PILLAR, SW_PILLAR, NE_PILLAR, SE_PILLAR);

    public ChestFrameBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(CONTAINS_BLOCK, false).with(LIGHT_LEVEL, 0).with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, HORIZONTAL_FACING, CONTAINS_BLOCK, LIGHT_LEVEL);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        FluidState fluidstate = context.getDimension().getFluidState(blockpos);
        if (fluidstate.getFluid() == Fluids.WATER) {
            return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(WATERLOGGED, fluidstate.isSource());
        } else {
            return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader dimension) {
        return Registration.CHEST_FRAME_TILE.get().create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World dimension, BlockPos pos, PlayerEntity player,
                                             Hand hand, BlockRayTraceResult result) {
        ItemStack item = player.getHeldItem(hand);
        if (!dimension.isClientSided) {
            TileEntity tileEntity = dimension.getTileEntity(pos);
            if (item.getItem() instanceof BlockItem) {
                int count = player.getHeldItem(hand).getCount();
                Block heldBlock = ((BlockItem) item.getItem()).getBlock();
                if (tileEntity instanceof ChestFrameTileEntity && !item.isEmpty() && BlockSavingHelper.isValidBlock(heldBlock) && !state.get(CONTAINS_BLOCK)) {
                    BlockState handBlockState = ((BlockItem) item.getItem()).getBlock().getDefaultState();
                    insertBlock(dimension, pos, state, handBlockState);
                    if (!player.isCreative())
                        player.getHeldItem(hand).setCount(count - 1);
                }
            }
            //hammer is needed to remove the block from the frame - you can change it in the config
            if (player.getHeldItem(hand).getItem() == Registration.HAMMER.get() || (!BCModConfig.HAMMER_NEEDED.get() && player.isSneaking())) {
                if (!player.isCreative())
                    this.dropContainedBlock(dimension, pos);
                state = state.with(CONTAINS_BLOCK, Boolean.FALSE);
                dimension.setBlockState(pos, state, 2);
            }
            BlockAppearanceHelper.setLightLevel(item, state, dimension, pos, player, hand);
            BlockAppearanceHelper.setTexture(item, state, dimension, player, pos);
            BlockAppearanceHelper.setDesign(dimension, pos, player, item);
            BlockAppearanceHelper.setDesignTexture(dimension, pos, player, item);
            if (tileEntity instanceof ChestFrameTileEntity && state.get(CONTAINS_BLOCK)) {
                if (!(Objects.requireNonNull(item.getItem().getRegistryName()).getNamespace().equals(QTextureModels.MOD_ID))) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, (ChestFrameTileEntity) tileEntity, pos);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void dropContainedBlock(World dimensionIn, BlockPos pos) {
        if (!dimensionIn.isClientSided) {
            TileEntity tileentity = dimensionIn.getTileEntity(pos);
            if (tileentity instanceof ChestFrameTileEntity) {
                ChestFrameTileEntity frameTileEntity = (ChestFrameTileEntity) tileentity;
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

    @Override
    public void insertBlock(IWorld dimensionIn, BlockPos pos, BlockState state, BlockState handBlock) {
        TileEntity tileentity = dimensionIn.getTileEntity(pos);
        if (tileentity instanceof ChestFrameTileEntity) {
            ChestFrameTileEntity frameTileEntity = (ChestFrameTileEntity) tileentity;
            frameTileEntity.clear();
            frameTileEntity.setMimic(handBlock);
            dimensionIn.setBlockState(pos, state.with(CONTAINS_BLOCK, Boolean.TRUE), 2);
        }
    }

    @Override
    public void onReplaced(BlockState state, World dimensionIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = dimensionIn.getTileEntity(pos);
            if (te instanceof ChestFrameTileEntity) {
                dropContainedBlock(dimensionIn, pos);
                InventoryHelper.dropItems(dimensionIn, pos, ((ChestFrameTileEntity) te).getItems());
            }
        }
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
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader dimensionIn, BlockPos pos, ISelectionContext context) {
        return CHEST;
    }
}
//========SOLI DEO GLORIA========//