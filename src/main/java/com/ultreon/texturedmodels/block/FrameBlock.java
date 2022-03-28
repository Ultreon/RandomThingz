package com.ultreon.texturedmodels.block;

import com.ultreon.texturedmodels.QTextureModels;
import com.ultreon.texturedmodels.setup.Registration;
import com.ultreon.texturedmodels.setup.config.BCModConfig;
import com.ultreon.texturedmodels.tileentity.FrameBlockEntity;
import com.ultreon.modlib.block.entity.Tickable;
import com.ultreon.texturedmodels.util.BCBlockStateProperties;
import com.ultreon.texturedmodels.util.BlockAppearanceHelper;
import com.ultreon.texturedmodels.util.BlockSavingHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraftforge.common.extensions.IForgeBlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.ultreon.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

/**
 * Main class for frameblocks - all important block info can be found here
 * This class is the most basic one for all frame blocks, so you can find most of the documentation here
 *
 * @author PianoManu
 * @version 1.7 10/20/20
 */
@SuppressWarnings("deprecation")
public class FrameBlock extends Block implements IForgeBlockState, SimpleWaterloggedBlock, EntityBlock {
    /**
     * Block property (can be seed when pressing F3 in-game)
     * This is needed, because we need to detect whether the blockstate has changed
     */
    public static final BooleanProperty CONTAINS_BLOCK = BCBlockStateProperties.CONTAINS_BLOCK;

    private static final VoxelShape MIDDLE_STRIP_NORTH = Block.box(0.0, 8.0, 1.0, 16.0, 9.0, 2.0);
    private static final VoxelShape MIDDLE_STRIP_EAST = Block.box(14.0, 8.0, 2.0, 15.0, 9.0, 14.0);
    private static final VoxelShape MIDDLE_STRIP_SOUTH = Block.box(0.0, 8.0, 14.0, 16.0, 9.0, 15.0);
    private static final VoxelShape MIDDLE_STRIP_WEST = Block.box(1.0, 8.0, 2.0, 2.0, 9.0, 14.0);
    private static final VoxelShape TOP = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape DOWN = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape NW_PILLAR = Block.box(0.0, 1.0, 0.0, 2.0, 15.0, 2.0);
    private static final VoxelShape SW_PILLAR = Block.box(0.0, 1.0, 14.0, 2.0, 15.0, 16.0);
    private static final VoxelShape NE_PILLAR = Block.box(14.0, 1.0, 0.0, 16.0, 15.0, 2.0);
    private static final VoxelShape SE_PILLAR = Block.box(14.0, 1.0, 14.0, 16.0, 15.0, 16.0);
    private static final VoxelShape MID = Block.box(2.0, 1.0, 2.0, 14.0, 15.0, 14.0);
    private static final VoxelShape CUBE = Shapes.or(MIDDLE_STRIP_EAST, MIDDLE_STRIP_SOUTH, MIDDLE_STRIP_WEST, MIDDLE_STRIP_NORTH, TOP, DOWN, NW_PILLAR, SW_PILLAR, NE_PILLAR, SE_PILLAR, MID);

    /**
     * classic constructor, all default values are set
     *
     * @param properties determined when registering the block (see {@linkplain Registration}
     */
    public FrameBlock(Properties properties) {
        super(properties.dynamicShape());
        this.registerDefaultState(this.stateDefinition.any().setValue(CONTAINS_BLOCK, Boolean.FALSE).setValue(LIGHT_LEVEL, 0).setValue(WATERLOGGED, false));//.with(TEXTURE,0));
    }

    /**
     * Assign needed blockstates to frame block - we need "contains_block" and "light_level", both because we have to check for blockstate changes
     */
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, CONTAINS_BLOCK, LIGHT_LEVEL);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new FrameBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return Tickable::blockEntity;
    }

    /**
     * Is called, whenever the block is right-clicked:
     * First it is checked, whether the dimension is remote (this has to be done client-side only).
     * Afterwards, we check, whether the held item is some sort of block item (e.g. logs, but not torches)
     * If that's the case, we ask for the tile entity of the frame and if the frame is empty, we fill it with the held block and remove the item from the player's inventory
     * If the frame is not empty and the player holds the hammer, the contained block is dropped into the dimension
     *
     * @param state     state of the block that is clicked
     * @param dimension dimension the block is placed in
     * @param pos       position (x,y,z) of block
     * @param player    entity of the player that includes all important information (health, armor, inventory,
     * @param hand      which hand is used (e.g. you have a sword in your main hand and an axe in your off-hand and right click a log -> you use the off-hand, not the main hand)
     * @param trace     to determine which part of the block is clicked (upper half, lower half, right side, left side, corners...)
     * @return see {@linkplain ActionResultType}
     */
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level dimension, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult trace) {
        ItemStack item = player.getItemInHand(hand);
        if (!dimension.isClientSide) {
            BlockAppearanceHelper.setLightLevel(item, state, dimension, pos, player, hand);
            BlockAppearanceHelper.setTexture(item, state, dimension, player, pos);
            BlockAppearanceHelper.setDesign(dimension, pos, player, item);
            BlockAppearanceHelper.setDesignTexture(dimension, pos, player, item);
            BlockAppearanceHelper.setOverlay(dimension, pos, player, item);
            if (item.getItem() instanceof BlockItem) {
                if (state.getValue(BCBlockStateProperties.CONTAINS_BLOCK) || Objects.requireNonNull(item.getItem().getRegistryName()).getNamespace().equals(QTextureModels.MOD_ID)) {
                    return InteractionResult.PASS;
                }
                BlockEntity tileEntity = dimension.getBlockEntity(pos);
                int count = player.getItemInHand(hand).getCount();
                Block heldBlock = ((BlockItem) item.getItem()).getBlock();
                if (tileEntity instanceof FrameBlockEntity && !item.isEmpty() && BlockSavingHelper.isValidBlock(heldBlock) && !state.getValue(CONTAINS_BLOCK)) {
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
        }
        return InteractionResult.SUCCESS;
    }

    /**
     * Used to drop the contained block
     * We check the tile entity, get the block from the tile entity and drop it at the block pos plus some small random coords in the dimension
     *
     * @param dimensionIn the dimension where we drop the block
     * @param pos         the block position where we drop the block
     */
    protected void dropContainedBlock(Level dimensionIn, BlockPos pos) {
        if (!dimensionIn.isClientSide) {
            BlockEntity entity = dimensionIn.getBlockEntity(pos);
            if (entity instanceof FrameBlockEntity frameTileEntity) {
                BlockState blockState = frameTileEntity.getMimic();
                if (!(blockState == null)) {
                    dimensionIn.levelEvent(1010, pos, 0);
                    frameTileEntity.clear();
                    double d0 = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .15f;
                    double d1 = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .060000002f + 0.6D;
                    double d2 = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .15f;
                    ItemStack stack = new ItemStack(blockState.getBlock());
                    ItemEntity item = new ItemEntity(dimensionIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
                    item.setDefaultPickUpDelay();
                    dimensionIn.addFreshEntity(item);
                    frameTileEntity.clear();
                }
            }
        }
    }

    /**
     * Used to place a block in a frame. Therefor we need the tile entity of the block and set its mimic to the given block state.
     * Lastly, we update the block state (useful for observers or something, idk)
     *
     * @param dimensionIn the dimension where we drop the block
     * @param pos         the block position where we drop the block
     * @param state       the old block state
     * @param handBlock   the block state of the held block - the block we want to insert into the frame
     */
    public void insertBlock(LevelAccessor dimensionIn, BlockPos pos, BlockState state, BlockState handBlock) {
        BlockEntity tileentity = dimensionIn.getBlockEntity(pos);
        if (tileentity instanceof FrameBlockEntity frameTileEntity) {
            frameTileEntity.clear();
            frameTileEntity.setMimic(handBlock);
            dimensionIn.setBlock(pos, state.setValue(CONTAINS_BLOCK, Boolean.TRUE), 2);
        }
    }

    /**
     * This method is called, whenever the state of the block changes (e.g. the block is harvested)
     *
     * @param state       old blockstate
     * @param dimensionIn dimension of the block
     * @param pos         block position
     * @param newState    new blockstate
     * @param isMoving    whether the block has some sort of motion (should never be moving - false)
     */
    @Override
    public void onRemove(BlockState state, @NotNull Level dimensionIn, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            dropContainedBlock(dimensionIn, pos);

            super.onRemove(state, dimensionIn, pos, newState, isMoving);
        }
        if (state.getValue(WATERLOGGED)) {
            dimensionIn.getFluidTicks().schedule(new ScheduledTick<Fluid>(Fluids.WATER, pos, Fluids.WATER.getTickDelay(dimensionIn), 0));
        }
    }

    //unused
    public int setLightValue(BlockState state, int amount) {
        if (state.getValue(LIGHT_LEVEL) > 15) {
            return 15;
        }
        return state.getValue(LIGHT_LEVEL);
    }

    //unused
    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        //return this.isTransparent;
        return true;
    }

    /**
     * This method returns the light value of the block, i.e. the emitted light level
     *
     * @param state     state of the block
     * @param dimension dimension the block is in
     * @param pos       block position
     * @return new amount of light that is emitted by the block
     */
    @Override
    public int getLightEmission(BlockState state, BlockGetter dimension, BlockPos pos) {
        if (state.getValue(LIGHT_LEVEL) > 15) {
            return 15;
        }
        return state.getValue(LIGHT_LEVEL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        if (fluidstate.getType() == Fluids.WATER) {
            return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.isSource());
        } else {
            return this.defaultBlockState();
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor dimensionIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            dimensionIn.getFluidTicks().schedule(new ScheduledTick<>(Fluids.WATER, currentPos, Fluids.WATER.getTickDelay(dimensionIn), 0));
        }

        return super.updateShape(stateIn, facing, facingState, dimensionIn, currentPos, facingPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter dimensionIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (!state.getValue(CONTAINS_BLOCK)) {
            return CUBE;
        }
        return Shapes.block();
    }
}
