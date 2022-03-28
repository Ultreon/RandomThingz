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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

import static com.ultreon.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;

/**
 * Main class for frame stairs - all important block info can be found here
 * Visit {@linkplain FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.4 10/06/20
 */
public class StairsFrameBlock extends StairBlock implements EntityBlock {

    public static final BooleanProperty CONTAINS_BLOCK = BCBlockStateProperties.CONTAINS_BLOCK;

    public StairsFrameBlock(Supplier<BlockState> state, Properties properties) {
        super(state, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CONTAINS_BLOCK, Boolean.FALSE).setValue(FACING, Direction.NORTH).setValue(HALF, Half.BOTTOM).setValue(SHAPE, StairsShape.STRAIGHT).setValue(WATERLOGGED, Boolean.FALSE).setValue(LIGHT_LEVEL, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONTAINS_BLOCK, FACING, HALF, SHAPE, WATERLOGGED, LIGHT_LEVEL);
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
            BlockAppearanceHelper.setOverlay(dimension, pos, player, item);
            if (item.getItem() instanceof BlockItem) {
                if (state.getValue(BCBlockStateProperties.CONTAINS_BLOCK) || Objects.requireNonNull(item.getItem().getRegistryName()).getNamespace().equals(QTextureModels.MOD_ID)) {
                    return InteractionResult.PASS;
                }
                BlockEntity tileEntity = dimension.getBlockEntity(pos);
                int count = player.getItemInHand(hand).getCount();
                if (tileEntity instanceof FrameBlockEntity && !item.isEmpty() && BlockSavingHelper.isValidBlock(((BlockItem) item.getItem()).getBlock()) && !state.getValue(CONTAINS_BLOCK)) {
                    ((FrameBlockEntity) tileEntity).clear();
                    BlockState handBlockState = ((BlockItem) item.getItem()).getBlock().defaultBlockState();
                    ((FrameBlockEntity) tileEntity).setMimic(handBlockState);
                    insertBlock(dimension, pos, state, handBlockState);
                    if (!player.isCreative())
                        player.getItemInHand(hand).setCount(count - 1);

                }
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

    private void dropContainedBlock(Level dimensionIn, BlockPos pos) {
        if (!dimensionIn.isClientSide) {
            BlockEntity blockEntity = dimensionIn.getBlockEntity(pos);
            if (blockEntity instanceof FrameBlockEntity frameTileEntity) {
                BlockState blockState = frameTileEntity.getMimic();
                if (!(blockState == null)) {
                    dimensionIn.levelEvent(1010, pos, 0);
                    frameTileEntity.clear();
                    double d0 = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .15f;
                    double d1 = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .060000002f + 0.6D;
                    double d2 = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .15f;
                    ItemStack stack1 = new ItemStack(blockState.getBlock());
                    ItemEntity itementity = new ItemEntity(dimensionIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack1);
                    itementity.setDefaultPickUpDelay();
                    dimensionIn.addFreshEntity(itementity);
                    frameTileEntity.clear();
                }
            }
        }
    }

    public void insertBlock(LevelAccessor dimensionIn, BlockPos pos, BlockState state, BlockState handBlock) {
        BlockEntity blockEntity = dimensionIn.getBlockEntity(pos);
        if (blockEntity instanceof FrameBlockEntity frameTileEntity) {
            frameTileEntity.clear();
            frameTileEntity.setMimic(handBlock);
            dimensionIn.setBlock(pos, state.setValue(CONTAINS_BLOCK, Boolean.TRUE), 2);
        }
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level dimensionIn, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            dropContainedBlock(dimensionIn, pos);

            super.onRemove(state, dimensionIn, pos, newState, isMoving);
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
