package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.init.BlockInit;
import com.qsoftware.forgemod.init.ItemInit;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemListener {
    @SubscribeEvent
    public static void onItem(ItemEvent event) {
        if (event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_SWORD ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_AXE ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_PICKAXE ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_HOE ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_SHOVEL ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_HELMET ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_CHESTPLATE ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_LEGGINGS ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_BOOTS ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_INGOT ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_NUGGET ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_DUST ||
                event.getEntityItem().getItem().getItem() == BlockInit.INFINITY_BLOCK.get().asItem().getItem() ||
                event.getEntityItem().getItem().getItem() == BlockInit.INFINITY_ORE.get().asItem().getItem()) {
            event.getEntityItem().setInvulnerable(true);
        }
    }

}
