package com.ultreon.texturedmodels.block;

import com.ultreon.texturedmodels.setup.Registration;
import com.ultreon.texturedmodels.tileentity.FallingFrameBlockTile;
import com.ultreon.texturedmodels.tileentity.FrameBlockTile;
import com.ultreon.texturedmodels.util.BCBlockStateProperties;
import com.ultreon.texturedmodels.util.BlockAppearanceHelper;
import com.ultreon.texturedmodels.util.BlockSavingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

import static com.ultreon.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;

/**
 * Nothing important to see here, this class is currently unused, visit {@linkplain FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.1 10/06/20
 */
public class FallingFrameBlock extends FallingBlock {

    //TODO fix falling block losing tile entity
    public static final BooleanProperty CONTAINS_BLOCK = BCBlockStateProperties.CONTAINS_BLOCK;
    //public static final BlockContainerProperty CONTAINS = BCBlockStateProperties.CONTAINS;

    public FallingFrameBlock(Properties properties) {
        super(properties);
        //this.setDefaultState(this.getDefaultState().with(CONTAINS_BLOCK, false).with(CONTAINS, "empty").with(LIGHT_LEVEL, 0).with(TEXTURE,0));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        //builder.add(CONTAINS_BLOCK, CONTAINS, LIGHT_LEVEL, TEXTURE);
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
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World dimension, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        ItemStack item = player.getHeldItem(hand);
        if (!dimension.isClientSided) {
            if (state.get(CONTAINS_BLOCK)) {
                if (!player.isCreative())
                    this.dropContainedBlock(dimension, pos);
                state = state.with(CONTAINS_BLOCK, Boolean.FALSE);
                dimension.setBlockState(pos, state, 2);
            } else {
                if (item.getItem() instanceof BlockItem) {
                    TileEntity tileEntity = dimension.getTileEntity(pos);
                    int count = player.getHeldItem(hand).getCount();
                    if (tileEntity instanceof FrameBlockTile && !item.isEmpty() && BlockSavingHelper.isValidBlock(((BlockItem) item.getItem()).getBlock()) && !state.get(CONTAINS_BLOCK)) {
                        ((FrameBlockTile) tileEntity).clear();
                        BlockState handBlockState = ((BlockItem) item.getItem()).getBlock().getDefaultState();
                        ((FrameBlockTile) tileEntity).setMimic(handBlockState);
                        insertBlock(dimension, pos, state, handBlockState);
                        if (!player.isCreative())
                            player.getHeldItem(hand).setCount(count - 1);
                    }
                }
            }
            BlockAppearanceHelper.setLightLevel(item, state, dimension, pos, player, hand);
            BlockAppearanceHelper.setTexture(item, state, dimension, player, pos);
            if (item.getItem() == Registration.TEXTURE_WRENCH.get() && player.isSneaking()) {
                //System.out.println("You should rotate now!");
            }
        }
        return ActionResultType.SUCCESS;
    }

    private void dropContainedBlock(World dimensionIn, BlockPos pos) {
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

    @Override
    @SuppressWarnings("deprecation")
    public void onReplaced(BlockState state, World dimensionIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            dropContainedBlock(dimensionIn, pos);

            super.onReplaced(state, dimensionIn, pos, newState, isMoving);
        }
    }

    @Override
    protected void onStartFalling(FallingBlockEntity fallingEntity) {
        super.onStartFalling(fallingEntity);
    }

    @Override
    public void onEndFalling(World dimensionIn, BlockPos pos, BlockState fallingState, BlockState hitState, FallingBlockEntity entity) {
        super.onEndFalling(dimensionIn, pos, fallingState, hitState, entity);

    }

    @Override
    public void tick(BlockState state, ServerWorld dimensionIn, BlockPos pos, Random rand) {
        if (dimensionIn.isAirBlock(pos.down()) || canFallThrough(dimensionIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            if (dimensionIn.getTileEntity(pos) instanceof FrameBlockTile) {
                FrameBlockTile tileEntity = (FrameBlockTile) dimensionIn.getTileEntity(pos);
                if (tileEntity.getMimic() != null) {
                    //FallingFrameBlockEntity fallingFrameBlockEntity = new FallingFrameBlockEntity(dimensionIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, dimensionIn.getBlockState(pos), tileEntity.getMimic());
                    //this.onStartFalling(fallingFrameBlockEntity);
                    //dimensionIn.spawnEntity(fallingFrameBlockEntity);
                }
            }
        }
    }

    private TileEntity createFallingFrameTileEntity(BlockState mimic) {
        return new FallingFrameBlockTile(mimic);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader dimension, BlockPos pos) {
        if (state.get(LIGHT_LEVEL) > 15) {
            return 15;
        }
        return state.get(LIGHT_LEVEL);
    }
}
//========SOLI DEO GLORIA========//