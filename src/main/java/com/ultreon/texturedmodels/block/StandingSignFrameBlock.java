package com.ultreon.texturedmodels.block;

import com.ultreon.texturedmodels.tileentity.Tickable;
import com.ultreon.texturedmodels.tileentity.SignFrameBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

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
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0).setValue(WATERLOGGED, Boolean.FALSE).setValue(CONTAINS_BLOCK, false).setValue(LIGHT_LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, WATERLOGGED, CONTAINS_BLOCK, LIGHT_LEVEL);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SignFrameBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return Tickable::blockEntity;
    }

    @Override
    public InteractionResult use(BlockState state, Level dimensionIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        BlockEntity tile = dimensionIn.getBlockEntity(pos);
        if (tile instanceof SignFrameBlockEntity) {
            SignFrameBlockEntity signTile = (SignFrameBlockEntity) tile;
            player.openTextEdit(signTile);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
