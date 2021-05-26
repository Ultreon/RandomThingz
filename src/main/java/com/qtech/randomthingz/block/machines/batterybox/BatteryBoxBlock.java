package com.qtech.randomthingz.block.machines.batterybox;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BatteryBoxBlock extends Block {
    public static final IntegerProperty BATTERIES = IntegerProperty.create("batteries", 0, 6);

    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 13, 16);

    public BatteryBoxBlock() {
        super(Properties.generate(Material.IRON).hardnessAndResistance(6.0f, 20.0f).sound(SoundType.METAL));
        this.setDefaultState(this.getStateContainer().getBaseState().with(BATTERIES, 0));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader dimension) {
        return new BatteryBoxTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BATTERIES);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState();
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader dimensionIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World dimensionIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!dimensionIn.isClientSided) {
            this.interactWith(dimensionIn, pos, player);
        }
        return ActionResultType.SUCCESS;
    }

    public void interactWith(World dimensionIn, BlockPos pos, PlayerEntity player) {
        TileEntity tileEntity = dimensionIn.getTileEntity(pos);
        if (tileEntity instanceof BatteryBoxTileEntity) {
            player.openContainer((INamedContainerProvider) tileEntity);
        }
    }

    @Override
    public void onBlockPlacedBy(World dimensionIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = dimensionIn.getTileEntity(pos);
            if (tileentity instanceof BatteryBoxTileEntity) {
                ((BatteryBoxTileEntity) tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(BlockState state, World dimensionIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = dimensionIn.getTileEntity(pos);
            if (tileentity instanceof BatteryBoxTileEntity) {
                InventoryHelper.dropInventoryItems(dimensionIn, pos, (BatteryBoxTileEntity) tileentity);
                dimensionIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, dimensionIn, pos, newState, isMoving);
        }
    }
}
