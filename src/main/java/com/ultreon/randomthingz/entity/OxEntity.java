package com.ultreon.randomthingz.entity;

import com.ultreon.randomthingz.common.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Ox entity class.
 *
 * @author Qboi123
 */
public class OxEntity extends Cow {
    public OxEntity(EntityType<? extends Cow> type, Level dimensionIn) {
        super(type, dimensionIn);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public OxEntity getBreedOffspring(ServerLevel dimensionIn, AgableMob ageable) {  // createChild
        return ModEntities.OX.get().create(this.level);
    }
}
