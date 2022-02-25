package com.ultreon.texturedmodels.block;

import com.ultreon.texturedmodels.QTextureModels;
import com.ultreon.texturedmodels.setup.Registration;
import com.ultreon.texturedmodels.setup.config.BCModConfig;
import com.ultreon.texturedmodels.tileentity.ChestFrameBlockEntity;
import com.ultreon.texturedmodels.tileentity.Tickable;
import com.ultreon.texturedmodels.util.BlockAppearanceHelper;
import com.ultreon.texturedmodels.util.BlockSavingHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.ultreon.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

/**
 *for frame chests - all important block info can be found here
 * Visit {@linkplain FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.4 10/06/20
 */
public class ChestFrameBlock extends FrameBlock implements SimpleWaterloggedBlock {
    private static final VoxelShape INNER_CUBE = Block.box(2.0, 2.0, 2.0, 14.0, 14.0, 14.0);
    private static final VoxelShape BOTTOM_NORTH = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 2.0);
    private static final VoxelShape BOTTOM_EAST = Block.box(14.0, 0.0, 2.0, 16.0, 2.0, 14.0);
    private static final VoxelShape BOTTOM_SOUTH = Block.box(0.0, 0.0, 14.0, 16.0, 2.0, 16.0);
    private static final VoxelShape BOTTOM_WEST = Block.box(0.0, 0.0, 2.0, 2.0, 2.0, 14.0);
    private static final VoxelShape TOP_NORTH = Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 2.0);
    private static final VoxelShape TOP_EAST = Block.box(14.0, 14.0, 2.0, 16.0, 16.0, 14.0);
    private static final VoxelShape TOP_SOUTH = Block.box(0.0, 14.0, 14.0, 16.0, 16.0, 16.0);
    private static final VoxelShape TOP_WEST = Block.box(0.0, 14.0, 2.0, 2.0, 16.0, 14.0);
    private static final VoxelShape NW_PILLAR = Block.box(0.0, 2.0, 0.0, 2.0, 14.0, 2.0);
    private static final VoxelShape SW_PILLAR = Block.box(0.0, 2.0, 14.0, 2.0, 14.0, 16.0);
    private static final VoxelShape NE_PILLAR = Block.box(14.0, 2.0, 0.0, 16.0, 14.0, 2.0);
    private static final VoxelShape SE_PILLAR = Block.box(14.0, 2.0, 14.0, 16.0, 14.0, 16.0);
    private static final VoxelShape CHEST = Shapes.or(INNER_CUBE, BOTTOM_EAST, BOTTOM_SOUTH, BOTTOM_WEST, BOTTOM_NORTH, TOP_EAST, TOP_SOUTH, TOP_WEST, TOP_NORTH, NW_PILLAR, SW_PILLAR, NE_PILLAR, SE_PILLAR);

    public ChestFrameBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CONTAINS_BLOCK, false).setValue(LIGHT_LEVEL, 0).setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, HORIZONTAL_FACING, CONTAINS_BLOCK, LIGHT_LEVEL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        if (fluidstate.getType() == Fluids.WATER) {
            return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidstate.isSource());
        } else {
            return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
        }
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return Registration.CHEST_FRAME_TILE.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return Tickable::blockEntity;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level dimension, @NotNull BlockPos pos, Player player,
                                          @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        ItemStack item = player.getItemInHand(hand);
        if (!dimension.isClientSide) {
            BlockEntity tileEntity = dimension.getBlockEntity(pos);
            if (item.getItem() instanceof BlockItem) {
                int count = player.getItemInHand(hand).getCount();
                Block heldBlock = ((BlockItem) item.getItem()).getBlock();
                if (tileEntity instanceof ChestFrameBlockEntity && !item.isEmpty() && BlockSavingHelper.isValidBlock(heldBlock) && !state.getValue(CONTAINS_BLOCK)) {
                    BlockState handBlockState = ((BlockItem) item.getItem()).getBlock().defaultBlockState();
                    insertBlock(dimension, pos, state, handBlockState);
                    if (!player.isCreative())
                        player.getItemInHand(hand).setCount(count - 1);
                }
            }
            //hammer is needed to remove the block from the frame - you can change it in the config
            if (player.getItemInHand(hand).getItem() == Registration.HAMMER.get() || (!BCModConfig.HAMMER_NEEDED.get() && player.isShiftKeyDown())) {
                if (!player.isCreative())
                    this.dropContainedBlock(dimension, pos);
                state = state.setValue(CONTAINS_BLOCK, Boolean.FALSE);
                dimension.setBlock(pos, state, 2);
            }
            BlockAppearanceHelper.setLightLevel(item, state, dimension, pos, player, hand);
            BlockAppearanceHelper.setTexture(item, state, dimension, player, pos);
            BlockAppearanceHelper.setDesign(dimension, pos, player, item);
            BlockAppearanceHelper.setDesignTexture(dimension, pos, player, item);
            if (tileEntity instanceof ChestFrameBlockEntity && state.getValue(CONTAINS_BLOCK)) {
                if (!(Objects.requireNonNull(item.getItem().getRegistryName()).getNamespace().equals(QTextureModels.MOD_ID))) {
                    NetworkHooks.openGui((ServerPlayer) player, (ChestFrameBlockEntity) tileEntity, pos);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void dropContainedBlock(Level dimensionIn, BlockPos pos) {
        if (!dimensionIn.isClientSide) {
            BlockEntity entity = dimensionIn.getBlockEntity(pos);
            if (entity instanceof ChestFrameBlockEntity frameTileEntity) {
                BlockState state = frameTileEntity.getMimic();
                if (!(state == null)) {
                    dimensionIn.levelEvent(1010, pos, 0);
                    frameTileEntity.clearContent();
                    double x = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .15f;
                    double y = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .060000002f + 0.6D;
                    double z = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .15f;
                    ItemStack stack = new ItemStack(state.getBlock());
                    ItemEntity item = new ItemEntity(dimensionIn, (double) pos.getX() + x, (double) pos.getY() + y, (double) pos.getZ() + z, stack);
                    item.setDefaultPickUpDelay();
                    dimensionIn.addFreshEntity(item);
                    frameTileEntity.clearContent();
                }
            }
        }
    }

    @Override
    public void insertBlock(LevelAccessor dimensionIn, BlockPos pos, BlockState state, BlockState handBlock) {
        BlockEntity entity = dimensionIn.getBlockEntity(pos);
        if (entity instanceof ChestFrameBlockEntity frameTileEntity) {
            frameTileEntity.clearContent();
            frameTileEntity.setMimic(handBlock);
            dimensionIn.setBlock(pos, state.setValue(CONTAINS_BLOCK, Boolean.TRUE), 2);
        }
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level dimensionIn, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity te = dimensionIn.getBlockEntity(pos);
            if (te instanceof ChestFrameBlockEntity) {
                dropContainedBlock(dimensionIn, pos);
                Containers.dropContents(dimensionIn, pos, ((ChestFrameBlockEntity) te).getItems());
            }
        }
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.getFluidTicks().schedule(new ScheduledTick<>(Fluids.WATER, pos, Fluids.WATER.getTickDelay(level), 0));
        }

        return super.updateShape(state, facing, facingState, level, pos, facingPos);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter dimensionIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return CHEST;
    }
}
