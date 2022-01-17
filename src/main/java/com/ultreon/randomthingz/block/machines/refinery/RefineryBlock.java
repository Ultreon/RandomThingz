package com.ultreon.randomthingz.block.machines.refinery;

import com.ultreon.randomthingz.block.machines.AbstractMachineBlock;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.texturedmodels.tileentity.ITickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class RefineryBlock extends AbstractMachineBlock {

    private static final VoxelShape SHAPE = Shapes.or(Block.box(1, 0, 1, 15, 16, 15), Block.box(0, 0, 0, 16, 3, 16));

    public RefineryBlock() {
        super(MachineTier.STANDARD, BlockBehaviour.Properties.of(Material.METAL).strength(6, 20).sound(SoundType.METAL));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter dimensionIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void openContainer(Level dimensionIn, BlockPos pos, Player player) {
        BlockEntity tileEntity = dimensionIn.getBlockEntity(pos);
        if (tileEntity instanceof MenuProvider) {
            player.openMenu((MenuProvider) tileEntity);
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RefineryBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ITickable::tickTE;
    }

    @Override
    public void animateTick(BlockState stateIn, Level dimensionIn, BlockPos pos, Random rand) {
        // TODO: Unique sound and particles? Copied from BlastFurnaceBlock.
        if (stateIn.getValue(LIT)) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = pos.getY();
            double d2 = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                dimensionIn.playLocalSound(d0, d1, d2, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f, false);
            }

            Direction direction = stateIn.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
            double d6 = rand.nextDouble() * 9.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
            dimensionIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }
}
