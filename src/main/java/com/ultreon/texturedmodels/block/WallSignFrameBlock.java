package com.ultreon.texturedmodels.block;

import com.ultreon.texturedmodels.tileentity.SignFrameTile;
import com.ultreon.texturedmodels.util.BCBlockStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Main class for wall frame signs - all important block info can be found here
 * Visit {@linkplain FrameBlock} for a better documentation
 * @author PianoManu
 * @version 1.1 09/25/20
 */
public class WallSignFrameBlock extends WallSignBlock {
    public WallSignFrameBlock(Properties propertiesIn) {
        super(propertiesIn, WoodType.OAK);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE).with(BCBlockStateProperties.CONTAINS_BLOCK, false).with(BCBlockStateProperties.LIGHT_LEVEL, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, BCBlockStateProperties.CONTAINS_BLOCK, BCBlockStateProperties.LIGHT_LEVEL);
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