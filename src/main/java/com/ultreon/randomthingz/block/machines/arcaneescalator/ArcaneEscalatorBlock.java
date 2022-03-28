package com.ultreon.randomthingz.block.machines.arcaneescalator;

import com.ultreon.randomthingz.block.machines.MachineType;
import com.ultreon.randomthingz.block.machines.MachineBlock;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.modlib.block.entity.Tickable;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ArcaneEscalatorBlock extends MachineBlock {
    public ArcaneEscalatorBlock(MachineTier tier) {
        super(tier, Properties.of(Material.METAL).strength(6f, 20f).sound(SoundType.METAL));
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity tileEntity = level.getBlockEntity(pos);
        if (tileEntity instanceof ArcaneEscalatorBlockEntity) {
            player.openMenu((MenuProvider) tileEntity);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return MachineType.ARCANE_ESCALATOR.getTileEntityType(tier).create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return Tickable::blockEntity;
    }

    @Override
    public void animateTick(BlockState stateIn, Level level, BlockPos pos, Random rand) {
        // TODO: Unique sound and particles? Copied from BlastFurnaceBlock.
        if (stateIn.getValue(LIT)) {
            double spawnX = (double) pos.getX() + 0.5D;
            double spawnY = pos.getY();
            double spawnZ = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                level.playLocalSound(spawnX, spawnY, spawnZ, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1f, 1f, false);
            }

            Direction facing = stateIn.getValue(FACING);
            Direction.Axis axis = facing.getAxis();
            final double delta = 0.52d;
            double altDelta = rand.nextDouble() * 0.6D - 0.3D;
            double deltaX = axis == Direction.Axis.X ? (double) facing.getStepX() * delta : altDelta;
            double deltaY = rand.nextDouble() * 9.0D / 16.0D;
            double deltaZ = axis == Direction.Axis.Z ? (double) facing.getStepZ() * delta : altDelta;
            level.addParticle(ParticleTypes.SMOKE, spawnX + deltaX, spawnY + deltaY, spawnZ + deltaZ, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof final ArcaneEscalatorBlockEntity be) {
            final MenuProvider container = new SimpleMenuProvider((id, inv, p) -> new ArcaneEscalatorContainer(id, inv, tier, be.inventory, pos, be.getFields()), be.getDisplayName());
            NetworkHooks.openGui((ServerPlayer) player, container, pos);
        }

        return InteractionResult.SUCCESS;
    }
}
