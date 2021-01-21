package com.qsoftware.forgemod.modules.entities.objects;

import com.qsoftware.forgemod.modules.entities.ModEntities;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Wrat hog entity class.
 *
 * @author Qboi123
 */
public class WarthogEntity extends HogEntity {
    public WarthogEntity(EntityType<? extends WarthogEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public WarthogEntity func_241840_a(ServerWorld worldIn, AgeableEntity ageable) {  // createChild
        return ModEntities.WARTHOG.get().create(this.world);
    }
}
