package com.qsoftware.forgemod.listener;

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
//        if (event.getEntityLiving() instanceof PlayerEntity) {
//            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
//
//            if (player.getUniqueID().toString().equals("43e3b67b-688b-4dae-b2f2-4e986c951ce0")) {
//                GhostPlayerEntity ghost = new GhostPlayerEntity(player.getEntityWorld(), player.getUniqueID(), player.getName().getFormattedText());
//                ghost.setPosition(player.getPosX() + 0.5, player.getPosY() + 0.1, player.getPosZ() + 0.5);
//                player.getEntityWorld().addEntity(ghost);
//            }
//        }
    }
}
