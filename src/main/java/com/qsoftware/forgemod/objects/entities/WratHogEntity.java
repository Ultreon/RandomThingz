package com.qsoftware.forgemod.objects.entities;

import com.qsoftware.forgemod.init.types.EntityTypeInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class WratHogEntity extends HogEntity {
    public WratHogEntity(EntityType<? extends WratHogEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public WratHogEntity func_241840_a(ServerWorld worldIn, AgeableEntity ageable) {  // createChild
        return EntityTypeInit.WRAT_HOG_ENTITY.create(this.world);
    }
}
