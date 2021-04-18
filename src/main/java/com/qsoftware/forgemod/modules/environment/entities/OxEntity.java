package com.qsoftware.forgemod.modules.environment.entities;

import com.qsoftware.forgemod.modules.environment.ModEntities;
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
 * Ox entity class.
 *
 * @author Qboi123
 */
public class OxEntity extends CowEntity {
    public OxEntity(EntityType<? extends CowEntity> type, World dimensionIn) {
        super(type, dimensionIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public OxEntity createChild(ServerWorld dimensionIn, AgeableEntity ageable) {  // createChild
        return ModEntities.OX.get().create(this.dimension);
    }
}
