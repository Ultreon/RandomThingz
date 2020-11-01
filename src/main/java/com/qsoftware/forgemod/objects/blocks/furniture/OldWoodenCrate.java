package com.qsoftware.forgemod.objects.blocks.furniture;

import com.qsoftware.forgemod.init.types.TileEntityTypesInit;
import com.qsoftware.forgemod.objects.items.base.FaceableBlock;
import com.qsoftware.forgemod.objects.tileentity.ExampleChestTileEntity;
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

@SuppressWarnings("deprecation")
public class OldWoodenCrate extends FaceableBlock {
    public OldWoodenCrate(Block.Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityTypesInit.EXAMPLE_CHEST.create();
    }

    @Override
    public @NotNull ActionResultType onBlockActivated(@NotNull BlockState state, World worldIn, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull Hand handIn, @NotNull BlockRayTraceResult result) {
        if (!worldIn.isRemote()) {
            TileEntity tile = worldIn.getTileEntity(pos);

            if (tile instanceof ExampleChestTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (ExampleChestTileEntity)tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
//        return super.onBlockActivated(state, worldIn, pos, player, handIn, result);
    }

    @Override
    public void onReplaced(BlockState state, @NotNull World worldIn, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof ExampleChestTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, new Inventory(((ExampleChestTileEntity)te).getItems().toArray(new ItemStack[]{})));
            }
        }
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof ExampleChestTileEntity) {
            InventoryHelper.dropInventoryItems(world, pos, ((ExampleChestTileEntity)te).getInventory());
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }


    @Override
    public void onPlayerDestroy(@NotNull IWorld worldIn, @NotNull BlockPos pos, @NotNull BlockState state) {
        super.onPlayerDestroy(worldIn, pos, state);
    }

    @Override
    public void onExplosionDestroy(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull Explosion explosionIn) {
        super.onExplosionDestroy(worldIn, pos, explosionIn);

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof ExampleChestTileEntity) {
            InventoryHelper.dropInventoryItems(worldIn, pos, new Inventory(((ExampleChestTileEntity)te).getItems().toArray(new ItemStack[]{})));
        }
    }
}
