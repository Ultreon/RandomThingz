package com.ultreon.randomthingz.block.machines.quarry;

import com.ultreon.randomthingz.block.machines.AbstractMachineBlock;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class QuarryBlock extends AbstractMachineBlock {

    private static final VoxelShape SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 16, 16));
    private final MachineTier defaultTier;

    public QuarryBlock(MachineTier defaultTier) {
        super(MachineTier.HEAVY, Properties.of(Material.METAL).strength(6, 20).sound(SoundType.METAL));
        this.defaultTier = defaultTier;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter dimensionIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter dimension, BlockPos pos) {
        return 0;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
//        return super.getStateForPlacement(context);
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull
    BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull
    BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//        return new QuarryTileEntity(this.defaultTier);
        return new QuarryBlockEntity(pos, state);
    }

    @Override
    protected void openContainer(Level dimensionIn, BlockPos pos, Player player) {
        BlockEntity tileEntity = dimensionIn.getBlockEntity(pos);
        if (tileEntity instanceof MenuProvider) {
            player.openMenu((MenuProvider) tileEntity);
        }
    }

    public MachineTier getDefaultTier() {
        return defaultTier;
    }
}
