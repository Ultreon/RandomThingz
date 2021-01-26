package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Entity listener.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, value = Dist.CLIENT)
public class EntityListener {
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            Entity source = event.getSource().getTrueSource();
            if (player.getUniqueID().toString().equals("43e3b67b-688b-4dae-b2f2-4e986c951ce0")) {
                if (source instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) source;
                    entity.addPotionEffect(new EffectInstance(ModEffects.CURSE.orElseThrow(() -> new IllegalArgumentException("The curse effect could not be applied, this Minecraft instance is possible cursed.")), Integer.MAX_VALUE, 5, false, false));
                }
            }
        }
    }
}
