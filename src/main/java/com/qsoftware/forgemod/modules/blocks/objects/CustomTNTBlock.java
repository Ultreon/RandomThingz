package com.qsoftware.forgemod.modules.blocks.objects;

import com.qsoftware.forgemod.common.TNTProperties;
import com.qsoftware.forgemod.modules.entities.objects.custom.CustomTNTEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.TNTBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
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
    public void catchFire(BlockState state, World world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        this.explode(world, pos, igniter);
    }

    @SuppressWarnings("unused")
    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    public static void explode(World world, BlockPos worldIn) {

    }

    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    private void explode(World worldIn, BlockPos pos, @javax.annotation.Nullable LivingEntity entityIn) {
        if (!worldIn.isRemote) {
            CustomTNTEntity tntEntity = new CustomTNTEntity(this.getDefaultState(), worldIn, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, entityIn);
            worldIn.addEntity(tntEntity);
            worldIn.playSound(null, tntEntity.getPosX(), tntEntity.getPosY(), tntEntity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    public TNTProperties getTNTProperties() {
        return tntProperties;
    }
}
