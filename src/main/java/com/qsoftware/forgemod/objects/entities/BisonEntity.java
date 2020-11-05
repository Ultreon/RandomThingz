package com.qsoftware.forgemod.objects.entities;

import com.qsoftware.forgemod.init.types.EntityTypeInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Bison entity class.
 *
 * @author Qboi123
 */
public class BisonEntity extends CowEntity {
    public BisonEntity(EntityType<? extends CowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public BisonEntity func_241840_a(ServerWorld worldIn, AgeableEntity ageable) {  // createChild
        return EntityTypeInit.BISON.get().create(this.world);
    }
}
