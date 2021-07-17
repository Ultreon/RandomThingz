package com.qtech.randomthingz.util;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.effect.common.ModEffects;
import lombok.experimental.UtilityClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
@UtilityClass
public class GodUtils {
    private static final List<String> uuids = new ArrayList<>();

    static {
        uuids.add("43e3b67b-688b-4dae-b2f2-4e986c951ce0");
        uuids.add("359f615d-fd46-4c7e-a882-4fa86aeea729");
        uuids.add("1e3b3d48-2b8d-4c7d-b806-63c0b22cb765");
    }

    public static boolean isGod(PlayerEntity entity) {
        return isGod(entity.getUniqueID());
    }

    private static boolean isGod(UUID uniqueID) {
        return isGod(uniqueID.toString());
    }

    private static boolean isGod(String uniqueID) {
        return uuids.contains(uniqueID);
    }

    @SubscribeEvent
    public static void onGodTick(TickEvent.PlayerTickEvent event) {
        if (isGod(event.player)) {
            if (event.player.getFoodStats().getFoodLevel() == 0) {
                event.player.heal(0.5f);
            }
            if (event.player.getAir() == 0) {
                event.player.heal(0.5f);
            }
            Objects.requireNonNull(event.player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(40.0d);
            Objects.requireNonNull(event.player.getAttribute(Attributes.LUCK)).setBaseValue(5.0d);
            event.player.heal(0.125f);

            if (event.player.isServerDimension()) {
                if (event.player instanceof ServerPlayerEntity) {
                    ServerPlayerEntity player = (ServerPlayerEntity) event.player;
                    player.abilities.allowFlying = true;
                    player.setAir(player.getMaxAir());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            Entity source = event.getSource().getTrueSource();
            if (isGod(player)) {
                if (source instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) source;
                    if (entity instanceof PlayerEntity) {
                        if (!isGod((PlayerEntity) entity)) {
                            entity.addPotionEffect(new EffectInstance(ModEffects.CURSE.orElseThrow(() -> new IllegalArgumentException("The curse effect could not be applied, this Minecraft instance is possible cursed.")), Integer.MAX_VALUE, 1, false, false));
                        }
                    } else {
                        entity.addPotionEffect(new EffectInstance(ModEffects.CURSE.orElseThrow(() -> new IllegalArgumentException("The curse effect could not be applied, this Minecraft instance is possible cursed.")), Integer.MAX_VALUE, 1, false, false));
                    }
                    event.setCanceled(true);
                    player.setHealth(player.getMaxHealth());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            Entity source = event.getSource().getTrueSource();
            if (isGod(player)) {
                if (source instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) source;
                    if (entity instanceof PlayerEntity) {
                        event.setCanceled(true);
                        entity.heal(event.getAmount());
                    }
                }
            }
        }
    }
}
