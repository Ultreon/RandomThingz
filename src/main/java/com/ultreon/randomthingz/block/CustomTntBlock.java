package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.TntProperties;
import com.ultreon.randomthingz.entity.custom.CustomPrimedTnt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Custom TNT block
 *
 * @param <T> the super block.
 * @author Qboi123
 */
@SuppressWarnings({"DeprecatedIsStillUsed", "unused"})
public abstract class CustomTntBlock<T extends CustomTntBlock<T>> extends TntBlock {
    private final TntProperties tntProperties;

    public CustomTntBlock(Properties properties, TntProperties tntProperties) {
        super(properties);
        this.tntProperties = tntProperties;
    }

    @Override
    public void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        this.explode(level, pos, igniter);
    }

    @SuppressWarnings("unused")
    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    public static void explode(Level dimension, BlockPos dimensionIn) {

    }

    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    private void explode(Level dimensionIn, BlockPos pos, @org.jetbrains.annotations.Nullable LivingEntity entityIn) {
        if (!dimensionIn.isClientSide) {
            CustomPrimedTnt tntEntity = new CustomPrimedTnt(this.defaultBlockState(), dimensionIn, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, entityIn);
            dimensionIn.addFreshEntity(tntEntity);
            dimensionIn.playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1f, 1f);
        }
    }

    @Override
    public void wasExploded(Level dimensionIn, BlockPos pos, Explosion explosionIn) {
        if (!dimensionIn.isClientSide) {
            CustomPrimedTnt tntEntity = new CustomPrimedTnt(this.defaultBlockState(), dimensionIn, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, explosionIn.getSourceMob());
            tntEntity.setFuse((short) (dimensionIn.random.nextInt(tntEntity.getFuse() / 4) + tntEntity.getFuse() / 8));
            dimensionIn.addFreshEntity(tntEntity);
        }
    }

    public TntProperties getTNTProperties() {
        return tntProperties;
    }

    public void afterExplosion(Level level, BlockPos pos, BlockState state, CustomPrimedTnt tntEntity) {

    }

    public void beforeExplosion(Level level, BlockPos entityBlockPosition, @Nullable BlockState blockState, CustomPrimedTnt customPrimedTnt) {

    }
}
