package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.blocks.ModBlocks;
import com.qsoftware.forgemod.modules.items.ModItems;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Item listener.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemListener {
    @SubscribeEvent
    public static void onItem(ItemEvent event) {
        if (event.getEntityItem().getItem().getItem() == ModItems.INFINITY_SWORD.get() ||
                event.getEntityItem().getItem().getItem() == ModItems.INFINITY_AXE.get() ||
                event.getEntityItem().getItem().getItem() == ModItems.INFINITY_PICKAXE.get() ||
                event.getEntityItem().getItem().getItem() == ModItems.INFINITY_HOE.get() ||
                event.getEntityItem().getItem().getItem() == ModItems.INFINITY_SHOVEL.get() ||
                event.getEntityItem().getItem().getItem() == ModItems.INFINITY_HELMET.get() ||
                event.getEntityItem().getItem().getItem() == ModItems.INFINITY_CHESTPLATE.get() ||
                event.getEntityItem().getItem().getItem() == ModItems.INFINITY_LEGGINGS.get() ||
                event.getEntityItem().getItem().getItem() == ModItems.INFINITY_BOOTS.get() ||
                event.getEntityItem().getItem().getItem() == ModItems.INFINITY_INGOT.get() ||
                event.getEntityItem().getItem().getItem() == ModItems.INFINITY_NUGGET.get() ||
                event.getEntityItem().getItem().getItem() == ModItems.INFINITY_DUST.get() ||
                event.getEntityItem().getItem().getItem() == ModBlocks.INFINITY_BLOCK.get().asItem().getItem() ||
                event.getEntityItem().getItem().getItem() == ModBlocks.INFINITY_ORE.get().asItem().getItem()) {
            event.getEntityItem().setInvulnerable(true);
        }
    }

}
