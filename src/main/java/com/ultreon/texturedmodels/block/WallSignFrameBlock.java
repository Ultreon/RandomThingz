package com.ultreon.texturedmodels.block;

import com.ultreon.modlib.block.entity.Tickable;
import com.ultreon.texturedmodels.tileentity.SignFrameBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.ultreon.texturedmodels.util.BCBlockStateProperties.CONTAINS_BLOCK;
import static com.ultreon.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;

/**
 * Main class for wall frame signs - all important block info can be found here
 * Visit {@linkplain FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.1 09/25/20
 */
public class WallSignFrameBlock extends WallSignBlock implements EntityBlock {
    public WallSignFrameBlock(Properties propertiesIn) {
        super(propertiesIn, WoodType.OAK);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE).setValue(CONTAINS_BLOCK, false).setValue(LIGHT_LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, CONTAINS_BLOCK, LIGHT_LEVEL);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        System.out.println("new tile");
        return new SignFrameBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level p_153212_, @NotNull BlockState p_153213_, @NotNull BlockEntityType<T> p_153214_) {
        return Tickable::blockEntity;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level dimensionIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
        BlockEntity tile = dimensionIn.getBlockEntity(pos);
        if (tile instanceof SignFrameBlockEntity signEntity) {
            player.openTextEdit(signEntity);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
