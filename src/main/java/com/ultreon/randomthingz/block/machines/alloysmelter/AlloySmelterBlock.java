package com.ultreon.randomthingz.block.machines.alloysmelter;

import com.ultreon.randomthingz.block._common.MachineType;
import com.ultreon.randomthingz.block.machines.AbstractMachineBlock;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;
import java.util.Random;

public class AlloySmelterBlock extends AbstractMachineBlock {
    public AlloySmelterBlock(MachineTier tier) {
        super(tier, BlockBehaviour.Properties.of(Material.METAL).strength(6.0f, 20.0f).sound(SoundType.METAL));
    }

    @Override
    protected void openContainer(Level dimensionIn, BlockPos pos, Player player) {
        BlockEntity tileEntity = dimensionIn.getBlockEntity(pos);
        if (tileEntity instanceof AlloySmelterTileEntity) {
            player.openMenu((MenuProvider) tileEntity);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockGetter dimensionIn) {
        return MachineType.ALLOY_SMELTER.getTileEntityType(tier).create();
    }

    @Override
    public void animateTick(BlockState stateIn, Level dimensionIn, BlockPos pos, Random rand) {
        // TODO: Unique sound and particles? Copied from BlastFurnaceBlock.
        if (stateIn.getValue(LIT)) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = pos.getY();
            double d2 = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                dimensionIn.playLocalSound(d0, d1, d2, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
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
