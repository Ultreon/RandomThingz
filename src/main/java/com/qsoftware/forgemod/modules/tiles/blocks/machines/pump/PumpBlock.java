package com.qsoftware.forgemod.modules.tiles.blocks.machines.pump;

import com.qsoftware.forgemod.common.enums.MachineTier;
import com.qsoftware.forgemod.modules.tiles.blocks.machines.AbstractMachineBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
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

public class PumpBlock extends AbstractMachineBlock {

    private static final VoxelShape SHAPE = VoxelShapes.or(Block.createCuboidShape(1, 0, 1, 15, 15, 15));

    public PumpBlock() {
        super(MachineTier.STANDARD, AbstractBlock.Properties.generate(Material.IRON).hardnessAndResistance(6, 20).sound(SoundType.METAL));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader dimensionIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
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

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(LIT);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader dimensionIn) {
        return new PumpTileEntity();
    }

    @Override
    protected void interactWith(World dimensionIn, BlockPos pos, PlayerEntity player) {
        TileEntity tileEntity = dimensionIn.getTileEntity(pos);
        if (tileEntity instanceof INamedContainerProvider) {
            player.openContainer((INamedContainerProvider) tileEntity);
        }
    }
}
