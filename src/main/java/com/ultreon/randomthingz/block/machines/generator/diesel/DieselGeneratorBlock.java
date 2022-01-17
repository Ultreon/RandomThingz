package com.ultreon.randomthingz.block.machines.generator.diesel;

import com.ultreon.randomthingz.block.machines.generator.BaseGeneratorBlock;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DieselGeneratorBlock extends BaseGeneratorBlock {
    public DieselGeneratorBlock() {
        super(MachineTier.STANDARD, BlockBehaviour.Properties.of(Material.METAL).strength(6, 20).sound(SoundType.METAL));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockGetter dimensionIn) {
        return new DieselGeneratorBlockEntity();
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
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
            double d6 = rand.nextDouble() * 9.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
            dimensionIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }
}
