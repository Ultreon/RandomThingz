package com.ultreon.randomthingz.block.machines.crusher;

import com.ultreon.modlib.block.entity.Tickable;
import com.ultreon.randomthingz.block.machines.MachineType;
import com.ultreon.randomthingz.block.machines.MachineBlock;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.init.ModStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CrusherBlock extends MachineBlock {
    public CrusherBlock(MachineTier tier) {
        super(tier, BlockBehaviour.Properties.of(Material.METAL).strength(6, 20).sound(SoundType.METAL));
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof CrusherBlockEntity) {
            player.openMenu((MenuProvider) entity);
            player.awardStat(ModStats.INTERACT_WITH_CRUSHER);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return MachineType.CRUSHER.getTileEntityType(tier).create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return Tickable::blockEntity;
    }

    @Override
    public void animateTick(BlockState stateIn, Level level, BlockPos pos, Random rand) {
        // TODO: Unique sound and particles? Copied from BlastFurnaceBlock.
        if (stateIn.getValue(LIT)) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = pos.getY();
            double d2 = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                level.playLocalSound(d0, d1, d2, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1f, 1f, false);
            }

            Direction direction = stateIn.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
            double d6 = rand.nextDouble() * 9.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
            level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof final CrusherBlockEntity be) {
            final MenuProvider container = new SimpleMenuProvider((id, inv, p) -> new CrusherContainer(id, inv, tier, be.inventory, pos, be.getFields()), be.getDisplayName());
            NetworkHooks.openGui((ServerPlayer) player, container, pos);
        }

        return InteractionResult.SUCCESS;
    }
}
