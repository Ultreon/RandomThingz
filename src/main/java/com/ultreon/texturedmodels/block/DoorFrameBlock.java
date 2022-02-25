package com.ultreon.texturedmodels.block;

import com.ultreon.texturedmodels.QTextureModels;
import com.ultreon.texturedmodels.setup.Registration;
import com.ultreon.texturedmodels.setup.config.BCModConfig;
import com.ultreon.texturedmodels.tileentity.FrameBlockEntity;
import com.ultreon.texturedmodels.tileentity.Tickable;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.ultreon.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;

/**
 * Main class for frame doors - all important block info can be found here
 * Visit {@linkplain FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.5 10/09/20
 */
public class DoorFrameBlock extends DoorBlock implements EntityBlock {
    public static final BooleanProperty CONTAINS_BLOCK = BCBlockStateProperties.CONTAINS_BLOCK;

    public DoorFrameBlock(Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any().setValue(CONTAINS_BLOCK, Boolean.FALSE).setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE).setValue(HINGE, DoorHingeSide.LEFT).setValue(POWERED, Boolean.FALSE).setValue(HALF, DoubleBlockHalf.LOWER).setValue(LIGHT_LEVEL, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONTAINS_BLOCK, FACING, OPEN, HINGE, POWERED, HALF, LIGHT_LEVEL);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new FrameBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level p_153212_, @NotNull BlockState p_153213_, @NotNull BlockEntityType<T> p_153214_) {
        return Tickable::blockEntity;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level dimension, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult trace) {
        ItemStack item = player.getItemInHand(hand);
        if (!dimension.isClientSide) {
            BlockAppearanceHelper.setLightLevel(item, state, dimension, pos, player, hand);
            BlockAppearanceHelper.setTexture(item, state, dimension, player, pos);
            BlockAppearanceHelper.setDesign(dimension, pos, player, item);
            BlockAppearanceHelper.setDesignTexture(dimension, pos, player, item);
            BlockAppearanceHelper.setGlassColor(dimension, pos, player, hand);
            BlockAppearanceHelper.setOverlay(dimension, pos, player, item);
            if (item.getItem() instanceof BlockItem) {
                BlockEntity tileEntity = dimension.getBlockEntity(pos);
                int count = player.getItemInHand(hand).getCount();
                if (tileEntity instanceof FrameBlockEntity && !item.isEmpty() && BlockSavingHelper.isValidBlock(((BlockItem) item.getItem()).getBlock()) && !state.getValue(CONTAINS_BLOCK)) {

                    ((FrameBlockEntity) tileEntity).clear();
                    BlockState handBlockState = ((BlockItem) item.getItem()).getBlock().defaultBlockState();
                    ((FrameBlockEntity) tileEntity).setMimic(handBlockState);
                    insertBlock(dimension, pos, state, handBlockState);
                    if (!player.isCreative())
                        player.getItemInHand(hand).setCount(count - 1);
                    return InteractionResult.SUCCESS;
                }
            }
            if (!Objects.requireNonNull(item.getItem().getRegistryName()).getNamespace().equals(QTextureModels.MOD_ID)) {
                if (state.getValue(DoorBlock.OPEN)) {
                    state = state.setValue(OPEN, false);
                } else {
                    state = state.setValue(OPEN, true);
                }
                dimension.setBlock(pos, state, 10);
                dimension.levelEvent(null, state.getValue(OPEN) ? 1006 : 1012, pos, 0);
            }
            if (player.getItemInHand(hand).getItem() == Registration.HAMMER.get() || (!BCModConfig.HAMMER_NEEDED.get() && player.isShiftKeyDown())) {
                if (!player.isCreative())
                    this.dropContainedBlock(dimension, pos);
                state = state.setValue(CONTAINS_BLOCK, Boolean.FALSE);
                dimension.setBlock(pos, state, 2);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void dropContainedBlock(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof FrameBlockEntity frameTileEntity) {
                BlockState blockState = frameTileEntity.getMimic();
                if (!(blockState == null)) {
                    level.levelEvent(1010, pos, 0);
                    frameTileEntity.clear();
                    double d0 = (double) (level.random.nextFloat() * .7f) + (double) .15f;
                    double d1 = (double) (level.random.nextFloat() * .7f) + (double) .060000002f + 0.6D;
                    double d2 = (double) (level.random.nextFloat() * .7f) + (double) .15f;
                    ItemStack stack1 = new ItemStack(blockState.getBlock());
                    ItemEntity itemEntity = new ItemEntity(level, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack1);
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);
                    frameTileEntity.clear();
                }
            }
        }
    }

    public void insertBlock(LevelAccessor level, BlockPos pos, BlockState state, BlockState handBlock) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof FrameBlockEntity frameTileEntity) {
            frameTileEntity.clear();
            frameTileEntity.setMimic(handBlock);
            level.setBlock(pos, state.setValue(CONTAINS_BLOCK, Boolean.TRUE), 2);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            dropContainedBlock(level, pos);

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter dimension, BlockPos pos) {
        if (state.getValue(LIGHT_LEVEL) > 15) {
            return 15;
        }
        return state.getValue(LIGHT_LEVEL);
    }
}
