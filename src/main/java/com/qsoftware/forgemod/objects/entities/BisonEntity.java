package com.qsoftware.forgemod.objects.entities;

import com.qsoftware.forgemod.init.types.EntityTypeInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BisonEntity extends CowEntity {
    public BisonEntity(EntityType<? extends CowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public BisonEntity func_241840_a(ServerWorld worldIn, AgeableEntity ageable) {  // createChild
        return EntityTypeInit.BISON_ENTITY.create(this.world);
    }
}
