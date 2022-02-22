package com.ultreon.texturedmodels.block;

import com.ultreon.texturedmodels.setup.Registration;
import com.ultreon.texturedmodels.tileentity.FallingFrameBlockEntity;
import com.ultreon.texturedmodels.tileentity.FrameBlockEntity;
import com.ultreon.texturedmodels.tileentity.ITickable;
import com.ultreon.texturedmodels.util.BCBlockStateProperties;
import com.ultreon.texturedmodels.util.BlockAppearanceHelper;
import com.ultreon.texturedmodels.util.BlockSavingHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static com.ultreon.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;

/**
 * Nothing important to see here, this class is currently unused, visit {@linkplain FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.1 10/06/20
 */
@SuppressWarnings("unused")
public class FallingFrameBlock extends FallingBlock implements EntityBlock {

    //TODO fix falling block losing tile entity
    public static final BooleanProperty CONTAINS_BLOCK = BCBlockStateProperties.CONTAINS_BLOCK;
    //public static final BlockContainerProperty CONTAINS = BCBlockStateProperties.CONTAINS;

    public FallingFrameBlock(Properties properties) {
        super(properties);
        //this.setDefaultState(this.getDefaultState().with(CONTAINS_BLOCK, false).with(CONTAINS, "empty").with(LIGHT_LEVEL, 0).with(TEXTURE,0));
    }

    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        //builder.add(CONTAINS_BLOCK, CONTAINS, LIGHT_LEVEL, TEXTURE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new FrameBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level p_153212_, @NotNull BlockState p_153213_, @NotNull BlockEntityType<T> p_153214_) {
        return ITickable::tickTE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState state, Level dimension, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult trace) {
        ItemStack item = player.getItemInHand(hand);
        if (!dimension.isClientSide) {
            if (state.getValue(CONTAINS_BLOCK)) {
                if (!player.isCreative())
                    this.dropContainedBlock(dimension, pos);
                state = state.setValue(CONTAINS_BLOCK, Boolean.FALSE);
                dimension.setBlock(pos, state, 2);
            } else {
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
                    }
                }
            }
            BlockAppearanceHelper.setLightLevel(item, state, dimension, pos, player, hand);
            BlockAppearanceHelper.setTexture(item, state, dimension, player, pos);
            if (item.getItem() == Registration.TEXTURE_WRENCH.get()) {
                player.isShiftKeyDown();
            }//System.out.println("You should rotate now!");
        }
        return InteractionResult.SUCCESS;
    }

    private void dropContainedBlock(Level dimensionIn, BlockPos pos) {
        if (!dimensionIn.isClientSide) {
            BlockEntity tileentity = dimensionIn.getBlockEntity(pos);
            if (tileentity instanceof FrameBlockEntity frameTileEntity) {
                BlockState blockState = frameTileEntity.getMimic();
                if (!(blockState == null)) {
                    dimensionIn.levelEvent(1010, pos, 0);
                    frameTileEntity.clear();
                    double d0 = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .15f;
                    double d1 = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .060000002f + 0.6D;
                    double d2 = (double) (dimensionIn.random.nextFloat() * .7f) + (double) .15f;
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
        if (tileentity instanceof FrameBlockEntity frameTileEntity) {
            frameTileEntity.clear();
            frameTileEntity.setMimic(handBlock);
            dimensionIn.setBlock(pos, state.setValue(CONTAINS_BLOCK, Boolean.TRUE), 2);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, @NotNull Level dimensionIn, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            dropContainedBlock(dimensionIn, pos);

            super.onRemove(state, dimensionIn, pos, newState, isMoving);
        }
    }

    @Override
    protected void falling(@NotNull FallingBlockEntity fallingEntity) {
        super.falling(fallingEntity);
    }

    @Override
    public void onLand(@NotNull Level dimensionIn, @NotNull BlockPos pos, @NotNull BlockState fallingState, @NotNull BlockState hitState, @NotNull FallingBlockEntity entity) {
        super.onLand(dimensionIn, pos, fallingState, hitState, entity);

    }

    @SuppressWarnings({"StatementWithEmptyBody", "CommentedOutCode"})
    @Override
    public void tick(@NotNull BlockState state, ServerLevel dimensionIn, BlockPos pos, @NotNull Random rand) {
        if (dimensionIn.isEmptyBlock(pos.below()) || isFree(dimensionIn.getBlockState(pos.below())) && pos.getY() >= 0) {
            if (dimensionIn.getBlockEntity(pos) instanceof FrameBlockEntity tileEntity) {
                if (tileEntity.getMimic() != null) {
                    //FallingFrameBlockEntity fallingFrameBlockEntity = new FallingFrameBlockEntity(dimensionIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, dimensionIn.getBlockState(pos), tileEntity.getMimic());
                    //this.onStartFalling(fallingFrameBlockEntity);
                    //dimensionIn.spawnEntity(fallingFrameBlockEntity);
                }
            }
        }
    }

    private BlockEntity createFallingFrameTileEntity(BlockPos pos, BlockState state) {
        return new FallingFrameBlockEntity(pos, state);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter dimension, BlockPos pos) {
        if (state.getValue(LIGHT_LEVEL) > 15) {
            return 15;
        }
        return state.getValue(LIGHT_LEVEL);
    }
}
