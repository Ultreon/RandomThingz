package com.qtech.randomthingz.block.furniture;

import com.qtech.randomthingz.item.type.FaceableBlock;
import com.qtech.randomthingz.modules.tiles.ModTileEntities;
import com.qtech.randomthingz.tileentity.CrateTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Wooden crate block class.
 *
 * @author Qboi123
 * @see CrateTileEntity
 */
@SuppressWarnings("deprecation")
public class WoodenCrateBlock extends FaceableBlock {
    public WoodenCrateBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader dimension) {
        return ModTileEntities.EXAMPLE_CHEST.get().create();
    }

    @Override
    public @NotNull ActionResultType onBlockActivated(@NotNull BlockState state, World dimensionIn, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull Hand handIn, @NotNull BlockRayTraceResult result) {
        if (!dimensionIn.isClientSided()) {
            TileEntity tile = dimensionIn.getTileEntity(pos);

            if (tile instanceof CrateTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (CrateTileEntity) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
//        return super.onBlockActivated(state, dimensionIn, pos, player, handIn, result);
    }

    @Override
    public void onReplaced(BlockState state, @NotNull World dimensionIn, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = dimensionIn.getTileEntity(pos);
            if (te instanceof CrateTileEntity) {
                InventoryHelper.dropInventoryItems(dimensionIn, pos, new Inventory(((CrateTileEntity) te).getItems().toArray(new ItemStack[]{})));
            }
        }
    }

    @Override
    public boolean removedByPlayer(BlockState state, World dimension, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        TileEntity te = dimension.getTileEntity(pos);
        if (te instanceof CrateTileEntity) {
            InventoryHelper.dropInventoryItems(dimension, pos, ((CrateTileEntity) te).getInventory());
        }
        return super.removedByPlayer(state, dimension, pos, player, willHarvest, fluid);
    }


    @Override
    public void onPlayerDestroy(@NotNull IWorld dimensionIn, @NotNull BlockPos pos, @NotNull BlockState state) {
        super.onPlayerDestroy(dimensionIn, pos, state);
    }

    @Override
    public void onExplosionDestroy(@NotNull World dimensionIn, @NotNull BlockPos pos, @NotNull Explosion explosionIn) {
        super.onExplosionDestroy(dimensionIn, pos, explosionIn);

        TileEntity te = dimensionIn.getTileEntity(pos);
        if (te instanceof CrateTileEntity) {
            InventoryHelper.dropInventoryItems(dimensionIn, pos, new Inventory(((CrateTileEntity) te).getItems().toArray(new ItemStack[]{})));
        }
    }
}
