package com.qtech.randomthingz.block;

import com.qtech.randomthingz.commons.TNTProperties;
import com.qtech.randomthingz.entity.custom.CustomTNTEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.TNTBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Custom TNT block
 *
 * @param <T> the super block.
 * @author Qboi123
 */
public abstract class CustomTNTBlock<T extends CustomTNTBlock<T>> extends TNTBlock {
    private final TNTProperties tntProperties;

    public CustomTNTBlock(Properties properties, TNTProperties tntProperties) {
        super(properties);
        this.tntProperties = tntProperties;
    }

    @Override
    public void catchFire(BlockState state, World dimension, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        this.explode(dimension, pos, igniter);
    }

    @SuppressWarnings("unused")
    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    public static void explode(World dimension, BlockPos dimensionIn) {

    }

    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    private void explode(World dimensionIn, BlockPos pos, @javax.annotation.Nullable LivingEntity entityIn) {
        if (!dimensionIn.isClientSided) {
            CustomTNTEntity tntEntity = new CustomTNTEntity(this.getDefaultState(), dimensionIn, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, entityIn);
            dimensionIn.spawnEntity(tntEntity);
            dimensionIn.playSound(null, tntEntity.getPosX(), tntEntity.getPosY(), tntEntity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public void onExplosionDestroy(World dimensionIn, BlockPos pos, Explosion explosionIn) {
        if (!dimensionIn.isClientSided) {
            CustomTNTEntity tntEntity = new CustomTNTEntity(this.getDefaultState(), dimensionIn, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, explosionIn.getExplosivePlacedBy());
            tntEntity.setFuse((short) (dimensionIn.rand.nextInt(tntEntity.getFuse() / 4) + tntEntity.getFuse() / 8));
            dimensionIn.spawnEntity(tntEntity);
        }
    }

    public TNTProperties getTNTProperties() {
        return tntProperties;
    }

    public void afterExplosion(World dimension, BlockPos pos, BlockState state, CustomTNTEntity tntEntity) {

    }

    public void beforeExplosion(World dimension, BlockPos entityBlockPosition, @Nullable BlockState blockState, CustomTNTEntity customTNTEntity) {

    }
}
