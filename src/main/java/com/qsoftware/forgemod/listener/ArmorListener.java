package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * Infinity armor listener.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmorListener {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerDamage(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            // Get player
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            // Get armor list.
            List<ItemStack> armor = (List<ItemStack>) player.getArmorInventoryList();

            // Check Armor
            if (!armor.isEmpty()) {
                if (armor.get(0).getItem().getRegistryName() == ModItems.INFINITY_BOOTS.getItem().getRegistryName()) {
                    if (armor.get(1).getItem().getRegistryName() == ModItems.INFINITY_LEGGINGS.get().getRegistryName()) {
                        if (armor.get(2).getItem().getRegistryName() == ModItems.INFINITY_CHESTPLATE.get().getRegistryName()) {
                            if (armor.get(3).getItem().getRegistryName() == ModItems.INFINITY_HELMET.get().getRegistryName()) {
                                // Set amount to zero, and cancel it.
                                event.setAmount(0);
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerAttacked(LivingAttackEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            // Get player
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            // Get armor list.
            List<ItemStack> armor = (List<ItemStack>) player.getArmorInventoryList();

            // Check Armor
            if (!armor.isEmpty()) {
                if (armor.get(0).getItem().equals(ModItems.INFINITY_BOOTS.get())) {
                    if (armor.get(1).getItem().equals(ModItems.INFINITY_LEGGINGS.get())) {
                        if (armor.get(2).getItem().equals(ModItems.INFINITY_CHESTPLATE.get())) {
                            if (armor.get(3).getItem().equals(ModItems.INFINITY_HELMET.get())) {

                                // Set amount to zero, and cancel it.
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            // Get player
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            // Get armor list.
            List<ItemStack> armor = (List<ItemStack>) player.getArmorInventoryList();

            // Check Armor
            if (!armor.isEmpty()) {
                if (armor.get(0).getItem().equals(ModItems.INFINITY_BOOTS.get())) {
                    if (armor.get(1).getItem().equals(ModItems.INFINITY_LEGGINGS.get())) {
                        if (armor.get(2).getItem().equals(ModItems.INFINITY_CHESTPLATE.get())) {
                            if (armor.get(3).getItem().equals(ModItems.INFINITY_HELMET.get())) {
                                // Set amount to zero, and cancel it.
                                event.setAmount(0);
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
