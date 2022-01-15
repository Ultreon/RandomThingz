package com.ultreon.texturedmodels.block;

import com.ultreon.texturedmodels.tileentity.SignFrameTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

import static com.ultreon.texturedmodels.util.BCBlockStateProperties.CONTAINS_BLOCK;
import static com.ultreon.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;

/**
 * Main class for wall frame signs - all important block info can be found here
 * Visit {@linkplain FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.1 09/25/20
 */
public class WallSignFrameBlock extends WallSignBlock {
    public WallSignFrameBlock(Properties propertiesIn) {
        super(propertiesIn, WoodType.OAK);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE).setValue(CONTAINS_BLOCK, false).setValue(LIGHT_LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, CONTAINS_BLOCK, LIGHT_LEVEL);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter dimension) {
        System.out.println("new tile");
        return new SignFrameTile();
    }

    @Override
    public InteractionResult use(BlockState state, Level dimensionIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        BlockEntity tile = dimensionIn.getBlockEntity(pos);
        if (tile instanceof SignFrameTile) {
            SignFrameTile signTile = (SignFrameTile) tile;
            player.openTextEdit(signTile);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
//========SOLI DEO GLORIA========//