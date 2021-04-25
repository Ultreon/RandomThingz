package com.qtech.forgemod.modules.tiles.blocks.machines.quarry;

import com.qtech.forgemod.common.enums.MachineTier;
import com.qtech.forgemod.modules.tiles.blocks.machines.AbstractMachineBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class QuarryBlock extends AbstractMachineBlock {

    private static final VoxelShape SHAPE = VoxelShapes.or(Block.createCuboidShape(0, 0, 0, 16, 16, 16));
    private final MachineTier defaultTier;

    public QuarryBlock(MachineTier defaultTier) {
        super(MachineTier.HEAVY, Properties.generate(Material.IRON).hardnessAndResistance(6, 20).sound(SoundType.METAL));
        this.defaultTier = defaultTier;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader dimensionIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader dimension, BlockPos pos) {
        return 0;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
//        return super.getStateForPlacement(context);
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader dimensionIn) {
//        return new QuarryTileEntity(this.defaultTier);
        return new QuarryTileEntity();
    }

    @Override
    protected void interactWith(World dimensionIn, BlockPos pos, PlayerEntity player) {
        TileEntity tileEntity = dimensionIn.getTileEntity(pos);
        if (tileEntity instanceof INamedContainerProvider) {
            player.openContainer((INamedContainerProvider) tileEntity);
        }
    }

    public MachineTier getDefaultTier() {
        return defaultTier;
    }
}
