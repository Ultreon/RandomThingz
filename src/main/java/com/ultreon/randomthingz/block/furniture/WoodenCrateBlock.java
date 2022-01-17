package com.ultreon.randomthingz.block.furniture;

import com.ultreon.randomthingz.block.DirectionalBlock;
import com.ultreon.randomthingz.block.entity.ModTileEntities;
import com.ultreon.randomthingz.tileentity.CrateTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Wooden crate block class.
 *
 * @author Qboi123
 * @see CrateTileEntity
 */
@SuppressWarnings("deprecation")
public class WoodenCrateBlock extends DirectionalBlock implements EntityBlock {
    public WoodenCrateBlock(Block.Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModTileEntities.EXAMPLE_CHEST.get().create(pos, state);
    }

    @Override
    public @NotNull
    InteractionResult use(@NotNull BlockState state, Level dimensionIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult result) {
        if (!dimensionIn.isClientSide()) {
            BlockEntity tile = dimensionIn.getBlockEntity(pos);

            if (tile instanceof CrateTileEntity) {
                NetworkHooks.openGui((ServerPlayer) player, (CrateTileEntity) tile, pos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
//        return super.onBlockActivated(state, dimensionIn, pos, player, handIn, result);
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level dimensionIn, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity te = dimensionIn.getBlockEntity(pos);
            if (te instanceof CrateTileEntity) {
                Containers.dropContents(dimensionIn, pos, new SimpleContainer(((CrateTileEntity) te).getItems().toArray(new ItemStack[]{})));
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level dimension, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockEntity te = dimension.getBlockEntity(pos);
        if (te instanceof CrateTileEntity) {
            Containers.dropContents(dimension, pos, ((CrateTileEntity) te).getInventory());
        }
        return super.onDestroyedByPlayer(state, dimension, pos, player, willHarvest, fluid);
    }


    @Override
    public void destroy(@NotNull LevelAccessor dimensionIn, @NotNull BlockPos pos, @NotNull BlockState state) {
        super.destroy(dimensionIn, pos, state);
    }

    @Override
    public void wasExploded(@NotNull Level dimensionIn, @NotNull BlockPos pos, @NotNull Explosion explosionIn) {
        super.wasExploded(dimensionIn, pos, explosionIn);

        BlockEntity te = dimensionIn.getBlockEntity(pos);
        if (te instanceof CrateTileEntity) {
            Containers.dropContents(dimensionIn, pos, new SimpleContainer(((CrateTileEntity) te).getItems().toArray(new ItemStack[]{})));
        }
    }
}
