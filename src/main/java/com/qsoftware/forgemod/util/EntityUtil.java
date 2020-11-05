package com.qsoftware.forgemod.util;

import com.google.common.annotations.Beta;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

/**
 * Entity utilities
 *
 * @author Qboi123
 */
@Beta
public final class EntityUtil {
    private EntityUtil() {

    }

    /**
     * Instant kills an living entity.
     *
     * @param entity the entity to kill.
     * @param damageSource the damage source, this is used for the death message.
     */
    public static void instantKill(LivingEntity entity, DamageSource damageSource) {
        // Redirect to default method.
        instantKill(entity, damageSource.damageType);
    }

    /**
     * Instant kills an living entity.
     *
     * @param entity the entity to kill
     * @param damageType the damage type, this is used for the death message.
     * @see LivingEntity#damageEntity(DamageSource, float)
     */
    @SuppressWarnings("JavadocReference")
    public static void instantKill(LivingEntity entity, String damageType) {
        // Set damage source
        DamageSource damageSrc = new DamageSource(damageType).setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode();

        entity.attackEntityFrom(damageSrc, Float.POSITIVE_INFINITY);

//        // Get health
//        float currentHealth = entity.getHealth();
//
//        // Forge hooks.
//        ForgeHooks.onLivingHurt(entity, damageSrc, currentHealth);
//        ForgeHooks.onLivingDamage(entity, damageSrc, currentHealth);
//
//        // Set health
//        entity.getCombatTracker().trackDamage(damageSrc, currentHealth, currentHealth); // QSoftware: changed f2 to currentHealth for instant kill
//        entity.setHealth(0f); // Forge: moved to fix MC-121048; QSoftware: changed to 0f for instant kill
//        entity.setAbsorptionAmount(0f); // QSoftware: changed to 0f for number compatibility
    }
}
