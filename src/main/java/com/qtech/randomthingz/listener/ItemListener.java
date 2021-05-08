package com.qtech.randomthingz.listener;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.item.common.ItemMaterial;
import com.qtech.randomthingz.item.tools.Tools;
import lombok.experimental.UtilityClass;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Item listener.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@UtilityClass
public class ItemListener {
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @SubscribeEvent
    public static void onItem(ItemEvent event) {
        if (event.getEntityItem().getItem().getItem() == Tools.INFINITY.getSword().get() ||
                event.getEntityItem().getItem().getItem() == Tools.INFINITY.getAxe().get() ||
                event.getEntityItem().getItem().getItem() == Tools.INFINITY.getPickaxe().get() ||
                event.getEntityItem().getItem().getItem() == Tools.INFINITY.getHoe().get() ||
                event.getEntityItem().getItem().getItem() == Tools.INFINITY.getShovel().get() ||
                event.getEntityItem().getItem().getItem() == Tools.INFINITY.getHelmet().get() ||
                event.getEntityItem().getItem().getItem() == Tools.INFINITY.getChestplate().get() ||
                event.getEntityItem().getItem().getItem() == Tools.INFINITY.getLeggings().get() ||
                event.getEntityItem().getItem().getItem() == Tools.INFINITY.getBoots().get() ||
                event.getEntityItem().getItem().getItem() == ItemMaterial.INFINITY.getIngot().get() ||
                event.getEntityItem().getItem().getItem() == ItemMaterial.INFINITY.getNugget().get() ||
                event.getEntityItem().getItem().getItem() == ItemMaterial.INFINITY.getDust().get() ||
                event.getEntityItem().getItem().getItem() == ItemMaterial.INFINITY.getStorageBlock().get().asItem().getItem() ||
                event.getEntityItem().getItem().getItem() == ItemMaterial.INFINITY.getOre().get().asItem().getItem()) {
            event.getEntityItem().setInvulnerable(true);
        }
    }
}
