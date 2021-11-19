package com.ultreon.texturedmodels.block;

import com.ultreon.texturedmodels.tileentity.SignFrameTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.ultreon.texturedmodels.util.BCBlockStateProperties.CONTAINS_BLOCK;
import static com.ultreon.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;

/**
 * Main class for standing frame signs - all important block info can be found here
 * Visit {@linkplain FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.1 09/25/20
 */
public class StandingSignFrameBlock extends StandingSignBlock {
    public StandingSignFrameBlock(Properties properties) {
        super(properties, WoodType.OAK);
        this.setDefaultState(this.stateContainer.getBaseState().with(ROTATION, 0).with(WATERLOGGED, Boolean.FALSE).with(CONTAINS_BLOCK, false).with(LIGHT_LEVEL, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, WATERLOGGED, CONTAINS_BLOCK, LIGHT_LEVEL);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader dimension) {
        System.out.println("new tile");
        return new SignFrameTile();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World dimensionIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity tile = dimensionIn.getTileEntity(pos);
        if (tile instanceof SignFrameTile) {
            SignFrameTile signTile = (SignFrameTile) tile;
            player.openSignEditor(signTile);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
//========SOLI DEO GLORIA========//