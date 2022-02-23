package com.ultreon.randomthingz.util;

import com.google.common.annotations.Beta;
import lombok.experimental.UtilityClass;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/**
 * Entity utilities
 *
 * @author Qboi123
 */
@Beta
@UtilityClass
public final class EntityUtils {
    /**
     * Instant kills an living entity.
     *
     * @param entity       the entity to kill.
     * @param damageSource the damage source, this is used for the death message.
     */
    public static void instantKill(LivingEntity entity, DamageSource damageSource) {
        // Redirect to default method.
        instantKill(entity, damageSource.msgId);
    }

    /**
     * Instant kills an living entity.
     *
     * @param entity     the entity to kill
     * @param damageType the damage type, this is used for the death message.
     * @see LivingEntity#damageEntity(DamageSource, float)
     */
    @SuppressWarnings("JavadocReference")
    public static void instantKill(LivingEntity entity, String damageType) {
        // Set damage source
        DamageSource damageSrc = new DamageSource(damageType).bypassArmor().bypassMagic().bypassInvul();

        entity.hurt(damageSrc, Float.POSITIVE_INFINITY);

//        // Get health
//        float currentHealth = entity.getHealth();
//
//        // Forge hooks.
//        ForgeHooks.onLivingHurt(entity, damageSrc, currentHealth);
//        ForgeHooks.onLivingDamage(entity, damageSrc, currentHealth);
//
//        // Set health
//        entity.getCombatTracker().recordDamage(damageSrc, currentHealth, currentHealth); // Ultreon Team: changed f2 to currentHealth for instant kill
//        entity.setHealth(0f); // Forge: moved to fix MC-121048; Ultreon Team: changed to 0f for instant kill
//        entity.setAbsorptionAmount(0f); // Ultreon Team: changed to 0f for number compatibility
    }

    /**
     * Heal the given entity to max health.
     *
     * @param entity the entity to heal.
     */
    public static void fullHeal(LivingEntity entity) {
        entity.heal(entity.getMaxHealth() - entity.getHealth());
    }
}
