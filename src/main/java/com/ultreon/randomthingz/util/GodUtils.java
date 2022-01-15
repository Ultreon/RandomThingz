package com.ultreon.randomthingz.util;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.effect.common.ModEffects;
import lombok.experimental.UtilityClass;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
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

    public static boolean isGod(Player entity) {
        return isGod(entity.getUUID());
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
            if (event.player.getFoodData().getFoodLevel() == 0) {
                event.player.heal(0.5f);
            }
            if (event.player.getAirSupply() == 0) {
                event.player.heal(0.5f);
            }
            Objects.requireNonNull(event.player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(40.0d);
            Objects.requireNonNull(event.player.getAttribute(Attributes.LUCK)).setBaseValue(5.0d);
            event.player.heal(0.125f);

            if (event.player.isEffectiveAi()) {
                if (event.player instanceof ServerPlayer) {
                    ServerPlayer player = (ServerPlayer) event.player;
                    player.abilities.mayfly = true;
                    player.setAirSupply(player.getMaxAirSupply());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();
            Entity source = event.getSource().getEntity();
            if (isGod(player)) {
                if (source instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) source;
                    if (entity instanceof Player) {
                        if (!isGod((Player) entity)) {
                            entity.addEffect(new MobEffectInstance(ModEffects.CURSE.orElseThrow(() -> new IllegalArgumentException("The curse effect could not be applied, this Minecraft instance is possible cursed.")), Integer.MAX_VALUE, 1, false, false));
                        }
                    } else {
                        entity.addEffect(new MobEffectInstance(ModEffects.CURSE.orElseThrow(() -> new IllegalArgumentException("The curse effect could not be applied, this Minecraft instance is possible cursed.")), Integer.MAX_VALUE, 1, false, false));
                    }
                    event.setCanceled(true);
                    player.setHealth(player.getMaxHealth());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();
            Entity source = event.getSource().getEntity();
            if (isGod(player)) {
                if (source instanceof LivingEntity) {
                    LivingEntity entity = (LivingEntity) source;
                    if (entity instanceof Player) {
                        event.setCanceled(true);
                        entity.heal(event.getAmount());
                    }
                }
            }
        }
    }
}
