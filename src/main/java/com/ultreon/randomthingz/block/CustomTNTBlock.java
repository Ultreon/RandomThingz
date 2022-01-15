package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.TNTProperties;
import com.ultreon.randomthingz.entity.custom.CustomTNTEntity;
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
public abstract class CustomTNTBlock<T extends CustomTNTBlock<T>> extends TntBlock {
    private final TNTProperties tntProperties;

    public CustomTNTBlock(Properties properties, TNTProperties tntProperties) {
        super(properties);
        this.tntProperties = tntProperties;
    }

    @Override
    public void catchFire(BlockState state, Level dimension, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        this.explode(dimension, pos, igniter);
    }

    @SuppressWarnings("unused")
    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    public static void explode(Level dimension, BlockPos dimensionIn) {

    }

    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    private void explode(Level dimensionIn, BlockPos pos, @javax.annotation.Nullable LivingEntity entityIn) {
        if (!dimensionIn.isClientSide) {
            CustomTNTEntity tntEntity = new CustomTNTEntity(this.defaultBlockState(), dimensionIn, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, entityIn);
            dimensionIn.addFreshEntity(tntEntity);
            dimensionIn.playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public void wasExploded(Level dimensionIn, BlockPos pos, Explosion explosionIn) {
        if (!dimensionIn.isClientSide) {
            CustomTNTEntity tntEntity = new CustomTNTEntity(this.defaultBlockState(), dimensionIn, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, explosionIn.getSourceMob());
            tntEntity.setFuse((short) (dimensionIn.random.nextInt(tntEntity.getLife() / 4) + tntEntity.getLife() / 8));
            dimensionIn.addFreshEntity(tntEntity);
        }
    }

    public TNTProperties getTNTProperties() {
        return tntProperties;
    }

    public void afterExplosion(Level dimension, BlockPos pos, BlockState state, CustomTNTEntity tntEntity) {

    }

    public void beforeExplosion(Level dimension, BlockPos entityBlockPosition, @Nullable BlockState blockState, CustomTNTEntity customTNTEntity) {

    }
}
