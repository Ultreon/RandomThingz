package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Entity listener.
 *
 * @author Qboi123
 */
public class EntityListener {
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            if (player.getUniqueID().toString().equals("43e3b67b-688b-4dae-b2f2-4e986c951ce0")) {
                if (event.getEntity() instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) event.getEntity();
                    entity.addPotionEffect(new EffectInstance(ModEffects.CURSE.orElseThrow(() -> new IllegalArgumentException("The curse effect could not be applied, this Minecraft instance is possible cursed."))));
                }
            }
        }
    }
}
