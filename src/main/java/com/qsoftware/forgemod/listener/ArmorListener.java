package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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
                if (armor.get(0).getItem().equals(ItemInit.INFINITY_BOOTS)) {
                    if (armor.get(1).getItem().equals(ItemInit.INFINITY_LEGGINGS)) {
                        if (armor.get(2).getItem().equals(ItemInit.INFINITY_CHESTPLATE)) {
                            if (armor.get(3).getItem().equals(ItemInit.INFINITY_HELMET)) {

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
                if (armor.get(0).getItem().equals(ItemInit.INFINITY_BOOTS)) {
                    if (armor.get(1).getItem().equals(ItemInit.INFINITY_LEGGINGS)) {
                        if (armor.get(2).getItem().equals(ItemInit.INFINITY_CHESTPLATE)) {
                            if (armor.get(3).getItem().equals(ItemInit.INFINITY_HELMET)) {

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
                if (armor.get(0).getItem().equals(ItemInit.INFINITY_BOOTS)) {
                    if (armor.get(1).getItem().equals(ItemInit.INFINITY_LEGGINGS)) {
                        if (armor.get(2).getItem().equals(ItemInit.INFINITY_CHESTPLATE)) {
                            if (armor.get(3).getItem().equals(ItemInit.INFINITY_HELMET)) {
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
