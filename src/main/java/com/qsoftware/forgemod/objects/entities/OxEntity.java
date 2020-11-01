package com.qsoftware.forgemod.objects.entities;

import com.qsoftware.forgemod.init.types.EntityTypeInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class OxEntity extends CowEntity {
    public OxEntity(EntityType<? extends CowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public OxEntity func_241840_a(ServerWorld worldIn, AgeableEntity ageable) {  // createChild
        return EntityTypeInit.OX_ENTITY.create(this.world);
    }
}
