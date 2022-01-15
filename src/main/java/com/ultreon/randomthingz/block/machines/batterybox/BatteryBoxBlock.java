package com.ultreon.randomthingz.block.machines.batterybox;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BatteryBoxBlock extends Block {
    public static final IntegerProperty BATTERIES = IntegerProperty.create("batteries", 0, 6);

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 13, 16);

    public BatteryBoxBlock() {
        super(Properties.of(Material.METAL).strength(6.0f, 20.0f).sound(SoundType.METAL));
        this.registerDefaultState(this.getStateDefinition().any().setValue(BATTERIES, 0));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter dimension) {
        return new BatteryBoxTileEntity();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BATTERIES);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter dimensionIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level dimensionIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!dimensionIn.isClientSide) {
            this.interactWith(dimensionIn, pos, player);
        }
        return InteractionResult.SUCCESS;
    }

    public void interactWith(Level dimensionIn, BlockPos pos, Player player) {
        BlockEntity tileEntity = dimensionIn.getBlockEntity(pos);
        if (tileEntity instanceof BatteryBoxTileEntity) {
            player.openMenu((MenuProvider) tileEntity);
        }
    }

    @Override
    public void setPlacedBy(Level dimensionIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity tileentity = dimensionIn.getBlockEntity(pos);
            if (tileentity instanceof BatteryBoxTileEntity) {
                ((BatteryBoxTileEntity) tileentity).setCustomName(stack.getHoverName());
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level dimensionIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileentity = dimensionIn.getBlockEntity(pos);
            if (tileentity instanceof BatteryBoxTileEntity) {
                Containers.dropContents(dimensionIn, pos, (BatteryBoxTileEntity) tileentity);
                dimensionIn.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, dimensionIn, pos, newState, isMoving);
        }
    }
}
