package com.qtech.texturedmodels.block;

import com.qtech.texturedmodels.QTextureModels;
import com.qtech.texturedmodels.setup.Registration;
import com.qtech.texturedmodels.setup.config.BCModConfig;
import com.qtech.texturedmodels.tileentity.BedFrameTile;
import com.qtech.texturedmodels.util.BCBlockStateProperties;
import com.qtech.texturedmodels.util.BlockAppearanceHelper;
import com.qtech.texturedmodels.util.BlockSavingHelper;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.*;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
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
        this.setDefaultState(this.stateContainer.getBaseState().with(CONTAINS_BLOCK, false).with(LIGHT_LEVEL, 0).with(PART, BedPart.FOOT).with(OCCUPIED, Boolean.FALSE).with(HORIZONTAL_FACING, Direction.NORTH));//.with(TEXTURE,0));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CONTAINS_BLOCK, LIGHT_LEVEL, PART, OCCUPIED, HORIZONTAL_FACING);//, TEXTURE);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader dimension) {
        return new BedFrameTile();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World dimension, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        ItemStack item = player.getHeldItem(hand);
        if (!dimension.isClientSided) {
            if ((state.get(CONTAINS_BLOCK) && !item.getItem().isIn(Tags.Items.DYES) && !Objects.requireNonNull(item.getItem().getRegistryName()).getNamespace().equals(QTextureModels.MOD_ID)) || item.isEmpty()) {
                //Taken from BedBlock, should work similar to vanilla beds
                if (state.get(PART) != BedPart.HEAD) {
                    pos = pos.offset(state.get(HORIZONTAL_FACING));
                    state = dimension.getBlockState(pos);
                    if (!state.matchesBlock(this)) {
                        return ActionResultType.CONSUME;
                    }
                }

                if (!doesBedWork(dimension)) {
                    dimension.deleteBlock(pos, false);
                    BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING).getOpposite());
                    if (dimension.getBlockState(blockpos).matchesBlock(this)) {
                        dimension.deleteBlock(blockpos, false);
                    }

                    dimension.createExplosion(null, DamageSource.causeBedExplosionDamage(), null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 5.0F, true, Explosion.Mode.DESTROY);
                    return ActionResultType.SUCCESS;
                } else if (state.get(OCCUPIED)) {
                    if (!this.func_226861_a_(dimension, pos)) {
                        player.sendStatusMessage(new TranslationTextComponent("block.minecraft.bed.occupied"), true);
                    }

                    return ActionResultType.SUCCESS;
                } else {
                    player.trySleep(pos).ifLeft((p_220173_1_) -> {
                        if (p_220173_1_ != null) {
                            player.sendStatusMessage(Objects.requireNonNull(p_220173_1_.getMessage()), true);
                        }

                    });
                    return ActionResultType.SUCCESS;
                }
            }
            if (state.get(CONTAINS_BLOCK) && player.isSneaking()) {
                this.dropContainedBlock(dimension, pos);
                state = state.with(CONTAINS_BLOCK, Boolean.FALSE);
                dimension.setBlockState(pos, state, 2);
            } else {
                if (item.getItem() instanceof BlockItem) {
                    TileEntity tileEntity = dimension.getTileEntity(pos);
                    int count = player.getHeldItem(hand).getCount();
                    Block heldBlock = ((BlockItem) item.getItem()).getBlock();
                    if (tileEntity instanceof BedFrameTile && !item.isEmpty() && BlockSavingHelper.isValidBlock(heldBlock) && !state.get(CONTAINS_BLOCK)) {
                        BlockState handBlockState = ((BlockItem) item.getItem()).getBlock().getDefaultState();
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
            BlockAppearanceHelper.setLightLevel(item, state, dimension, pos, player, hand);
            BlockAppearanceHelper.setTexture(item, state, dimension, player, pos);
            BlockAppearanceHelper.setDesign(dimension, pos, player, item);
            BlockAppearanceHelper.setDesignTexture(dimension, pos, player, item);
            BlockAppearanceHelper.setWoolColor(dimension, pos, player, hand);
            BlockAppearanceHelper.setOverlay(dimension, pos, player, item);
        }
        return ActionResultType.SUCCESS;
    }

    private boolean func_226861_a_(World dimension, BlockPos pos) {
        List<VillagerEntity> list = dimension.getEntitiesWithinBox(VillagerEntity.class, new AxisAlignedBB(pos), LivingEntity::isSleeping);
        if (list.isEmpty()) {
            return false;
        } else {
            list.get(0).wakeUp();
            return true;
        }
    }

    @SuppressWarnings("unused")
    protected void dropContainedBlock(World dimensionIn, BlockPos pos) {
        if (!dimensionIn.isClientSided) {
            TileEntity tileentity = dimensionIn.getTileEntity(pos);
            if (tileentity instanceof BedFrameTile) {
                BedFrameTile frameTileEntity = (BedFrameTile) tileentity;
                BlockState blockState = frameTileEntity.getMimic();
                if (!(blockState == null)) {
                    dimensionIn.playEvent(1010, pos, 0);
                    frameTileEntity.clear();
                    float f = 0.7F;
                    double d0 = (double) (dimensionIn.rand.nextFloat() * 0.7F) + (double) 0.15F;
                    double d1 = (double) (dimensionIn.rand.nextFloat() * 0.7F) + (double) 0.060000002F + 0.6D;
                    double d2 = (double) (dimensionIn.rand.nextFloat() * 0.7F) + (double) 0.15F;
                    ItemStack itemstack1 = blockState.getBlock().asItem().getDefaultInstance();
                    ItemEntity itementity = new ItemEntity(dimensionIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, itemstack1);
                    itementity.setDefaultPickupDelay();
                    dimensionIn.spawnEntity(itementity);
                }
                frameTileEntity.clear();
            }
        }
    }

    public void insertBlock(IWorld dimensionIn, BlockPos pos, BlockState state, BlockState handBlock) {
        TileEntity tileentity = dimensionIn.getTileEntity(pos);
        if (tileentity instanceof BedFrameTile) {
            BedFrameTile frameTileEntity = (BedFrameTile) tileentity;
            frameTileEntity.clear();
            frameTileEntity.setMimic(handBlock);
            dimensionIn.setBlockState(pos, state.with(CONTAINS_BLOCK, Boolean.TRUE), 2);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
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
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
//========SOLI DEO GLORIA========//