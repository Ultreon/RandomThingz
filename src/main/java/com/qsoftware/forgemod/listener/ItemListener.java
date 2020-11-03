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
        if (event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_SWORD.get() ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_AXE.get() ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_PICKAXE.get() ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_HOE.get() ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_SHOVEL.get() ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_HELMET.get() ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_CHESTPLATE.get() ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_LEGGINGS.get() ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_BOOTS.get() ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_INGOT.get() ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_NUGGET.get() ||
                event.getEntityItem().getItem().getItem() == ItemInit.INFINITY_DUST.get() ||
                event.getEntityItem().getItem().getItem() == BlockInit.INFINITY_BLOCK.get().asItem().getItem() ||
                event.getEntityItem().getItem().getItem() == BlockInit.INFINITY_ORE.get().asItem().getItem()) {
            event.getEntityItem().setInvulnerable(true);
        }
    }

}
