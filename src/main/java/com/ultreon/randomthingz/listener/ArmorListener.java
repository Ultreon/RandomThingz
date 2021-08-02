package com.ultreon.randomthingz.listener;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.item.tools.Toolset;
import lombok.experimental.UtilityClass;
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
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@UtilityClass
public class ArmorListener {
    /**
     * Event handler for living entity damage.<br>
     * This method cancels hurting player when the being equipped with infinity armor.<br>
     *
     * @param event a {@linkplain LivingDamageEvent living damage event} object.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerDamage(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            // Get player
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            // Get armor list.
            List<ItemStack> armor = (List<ItemStack>) player.getArmorInventoryList();

            // Check Armor
            if (!armor.isEmpty()) {
                if (armor.get(0).getItem().getRegistryName() == Toolset.INFINITY.getBoots().get().getRegistryName()) {
                    if (armor.get(1).getItem().getRegistryName() == Toolset.INFINITY.getLeggings().get().getRegistryName()) {
                        if (armor.get(2).getItem().getRegistryName() == Toolset.INFINITY.getChestplate().get().getRegistryName()) {
                            if (armor.get(3).getItem().getRegistryName() == Toolset.INFINITY.getBoots().get().getRegistryName()) {
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

    /**
     * Handles a living attack event.<br>
     * This method cancels hurting player when the being equipped with infinity armor.<br>
     *
     * @param event a {@linkplain LivingAttackEvent living attack event} object.
     */
    @SubscribeEvent
    public static void onPlayerAttacked(LivingAttackEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            // Get player
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            // Get armor list.
            List<ItemStack> armor = (List<ItemStack>) player.getArmorInventoryList();

            // Check Armor
            if (!armor.isEmpty()) {
                if (armor.get(0).getItem().getRegistryName() == Toolset.INFINITY.getBoots().get().getRegistryName()) {
                    if (armor.get(1).getItem().getRegistryName() == Toolset.INFINITY.getLeggings().get().getRegistryName()) {
                        if (armor.get(2).getItem().getRegistryName() == Toolset.INFINITY.getChestplate().get().getRegistryName()) {
                            if (armor.get(3).getItem().getRegistryName() == Toolset.INFINITY.getBoots().get().getRegistryName()) {

                                // Set amount to zero, and cancel it.
                                event.setCanceled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles a living hurt event.<br>
     * This method cancels hurting player when the being equipped with infinity armor.<br>
     *
     * @param event a {@linkplain LivingHurtEvent living hurt event} object.
     */
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            // Get player
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            // Get armor list.
            List<ItemStack> armor = (List<ItemStack>) player.getArmorInventoryList();

            // Check Armor
            if (!armor.isEmpty()) {
                if (armor.get(0).getItem().getRegistryName() == Toolset.INFINITY.getBoots().get().getRegistryName()) {
                    if (armor.get(1).getItem().getRegistryName() == Toolset.INFINITY.getLeggings().get().getRegistryName()) {
                        if (armor.get(2).getItem().getRegistryName() == Toolset.INFINITY.getChestplate().get().getRegistryName()) {
                            if (armor.get(3).getItem().getRegistryName() == Toolset.INFINITY.getBoots().get().getRegistryName()) {
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
