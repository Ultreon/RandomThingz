package com.ultreon.randomthingz.util;

import com.ultreon.randomthingz.RandomThingz;
import lombok.experimental.UtilityClass;
import net.minecraftforge.fml.common.Mod;

@Deprecated
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
@UtilityClass
public class QboiUtils {
//    @SubscribeEvent
//    public static void onQboiTick(TickEvent.PlayerTickEvent event) {
//        if (event.player.getUniqueID().toString().equals("43e3b67b-688b-4dae-b2f2-4e986c951ce0") ||
//                event.player.getUniqueID().toString().equals("359f615d-fd46-4c7e-a882-4fa86aeea729")) {
//            if (event.player.getFoodStats().getFoodLevel() == 0) {
//                event.player.heal(0.5f);
//            }
//            if (event.player.getAir() == 0) {
//                event.player.heal(0.5f);
//            }
//            Objects.requireNonNull(event.player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(40.0d);
//            Objects.requireNonNull(event.player.getAttribute(Attributes.LUCK)).setBaseValue(5.0d);
//        }
//    }
//
//    @SubscribeEvent
//    public static void onDeath(LivingDeathEvent event) {
//        if (event.getEntityLiving() instanceof PlayerEntity) {
//            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
//            Entity source = event.getSource().getTrueSource();
//            if (player.getUniqueID().toString().equals("43e3b67b-688b-4dae-b2f2-4e986c951ce0") ||
//                    player.getUniqueID().toString().equals("359f615d-fd46-4c7e-a882-4fa86aeea729")) {
//                if (source instanceof LivingEntity) {
//                    LivingEntity entity = (LivingEntity) source;
//                    entity.addPotionEffect(new EffectInstance(ModEffects.CURSE.orElseThrow(() -> new IllegalArgumentException("The curse effect could not be applied, this Minecraft instance is possible cursed.")), Integer.MAX_VALUE, 1, false, false));
//                }
//            }
//        }
//    }
}
