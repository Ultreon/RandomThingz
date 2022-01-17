package com.ultreon.texturedmodels.block;

import com.ultreon.texturedmodels.QTextureModels;
import com.ultreon.texturedmodels.setup.Registration;
import com.ultreon.texturedmodels.setup.config.BCModConfig;
import com.ultreon.texturedmodels.tileentity.BedFrameTile;
import com.ultreon.texturedmodels.tileentity.ITickable;
import com.ultreon.texturedmodels.util.BCBlockStateProperties;
import com.ultreon.texturedmodels.util.BlockAppearanceHelper;
import com.ultreon.texturedmodels.util.BlockSavingHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

/**
 * Main class for frame beds - all important block info can be found here
 * Visit {@linkplain FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.3 10/06/20
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BedFrameBlock extends BedBlock {
    public static final BooleanProperty CONTAINS_BLOCK = BCBlockStateProperties.CONTAINS_BLOCK;
    public static final IntegerProperty LIGHT_LEVEL = BCBlockStateProperties.LIGHT_LEVEL;
    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BedFrameBlock(DyeColor colorIn, Properties properties) {
        super(colorIn, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CONTAINS_BLOCK, false).setValue(LIGHT_LEVEL, 0).setValue(PART, BedPart.FOOT).setValue(OCCUPIED, Boolean.FALSE).setValue(HORIZONTAL_FACING, Direction.NORTH));//.with(TEXTURE,0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONTAINS_BLOCK, LIGHT_LEVEL, PART, OCCUPIED, HORIZONTAL_FACING);//, TEXTURE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BedFrameTile(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return ITickable::tickTE;
    }

    @Override
    public InteractionResult use(BlockState state, Level dimension, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        ItemStack item = player.getItemInHand(hand);
        if (!dimension.isClientSide) {
            if ((state.getValue(CONTAINS_BLOCK) && !item.is(Tags.Items.DYES) && !Objects.requireNonNull(item.getItem().getRegistryName()).getNamespace().equals(QTextureModels.MOD_ID)) || item.isEmpty()) {
                //Taken from BedBlock, should work similar to vanilla beds
                if (state.getValue(PART) != BedPart.HEAD) {
                    pos = pos.relative(state.getValue(HORIZONTAL_FACING));
                    state = dimension.getBlockState(pos);
                    if (!state.is(this)) {
                        return InteractionResult.CONSUME;
                    }
                }

                if (!canSetSpawn(dimension)) {
                    dimension.removeBlock(pos, false);
                    BlockPos blockpos = pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite());
                    if (dimension.getBlockState(blockpos).is(this)) {
                        dimension.removeBlock(blockpos, false);
                    }

                    dimension.explode(null, DamageSource.badRespawnPointExplosion(), null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 5.0f, true, Explosion.BlockInteraction.DESTROY);
                    return InteractionResult.SUCCESS;
                } else if (state.getValue(OCCUPIED)) {
                    if (!this.kickVillagerOutOfBed(dimension, pos)) {
                        player.displayClientMessage(new TranslatableComponent("block.minecraft.bed.occupied"), true);
                    }

                    return InteractionResult.SUCCESS;
                } else {
                    player.startSleepInBed(pos).ifLeft((p_220173_1_) -> {
                        if (p_220173_1_ != null) {
                            player.displayClientMessage(Objects.requireNonNull(p_220173_1_.getMessage()), true);
                        }

                    });
                    return InteractionResult.SUCCESS;
                }
            }
            if (state.getValue(CONTAINS_BLOCK) && player.isShiftKeyDown()) {
                this.dropContainedBlock(dimension, pos);
                state = state.setValue(CONTAINS_BLOCK, Boolean.FALSE);
                dimension.setBlock(pos, state, 2);
            } else {
                if (item.getItem() instanceof BlockItem) {
                    BlockEntity tileEntity = dimension.getBlockEntity(pos);
                    int count = player.getItemInHand(hand).getCount();
                    Block heldBlock = ((BlockItem) item.getItem()).getBlock();
                    if (tileEntity instanceof BedFrameTile && !item.isEmpty() && BlockSavingHelper.isValidBlock(heldBlock) && !state.getValue(CONTAINS_BLOCK)) {
                        BlockState handBlockState = ((BlockItem) item.getItem()).getBlock().defaultBlockState();
                        insertBlock(dimension, pos, state, handBlockState);
                        if (!player.isCreative())
                            player.getItemInHand(hand).setCount(count - 1);
                    }
                }
            }
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
            BlockAppearanceHelper.setWoolColor(dimension, pos, player, hand);
            BlockAppearanceHelper.setOverlay(dimension, pos, player, item);
        }
        return InteractionResult.SUCCESS;
    }

    private boolean kickVillagerOutOfBed(Level dimension, BlockPos pos) {
        List<Villager> list = dimension.getEntitiesOfClass(Villager.class, new AABB(pos), LivingEntity::isSleeping);
        if (list.isEmpty()) {
            return false;
        } else {
            list.get(0).stopSleeping();
            return true;
        }
    }

    @SuppressWarnings("unused")
    protected void dropContainedBlock(Level dimensionIn, BlockPos pos) {
        if (!dimensionIn.isClientSide) {
            BlockEntity entity = dimensionIn.getBlockEntity(pos);
            if (entity instanceof BedFrameTile frameTileEntity) {
                BlockState blockState = frameTileEntity.getMimic();
                if (!(blockState == null)) {
                    dimensionIn.levelEvent(1010, pos, 0);
                    frameTileEntity.clear();
                    float f = 0.7F;
                    double d0 = (double) (dimensionIn.random.nextFloat() * 0.7F) + (double) 0.15F;
                    double d1 = (double) (dimensionIn.random.nextFloat() * 0.7F) + (double) 0.060000002F + 0.6D;
                    double d2 = (double) (dimensionIn.random.nextFloat() * 0.7F) + (double) 0.15F;
                    ItemStack stack = blockState.getBlock().asItem().getDefaultInstance();
                    ItemEntity itementity = new ItemEntity(dimensionIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
                    itementity.setDefaultPickUpDelay();
                    dimensionIn.addFreshEntity(itementity);
                }
                frameTileEntity.clear();
            }
        }
    }

    public void insertBlock(LevelAccessor dimensionIn, BlockPos pos, BlockState state, BlockState handBlock) {
        BlockEntity entity = dimensionIn.getBlockEntity(pos);
        if (entity instanceof BedFrameTile frameTileEntity) {
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
    public int getLightEmission(BlockState state, BlockGetter dimension, BlockPos pos) {
        if (state.getValue(LIGHT_LEVEL) > 15) {
            return 15;
        }
        return state.getValue(LIGHT_LEVEL);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
