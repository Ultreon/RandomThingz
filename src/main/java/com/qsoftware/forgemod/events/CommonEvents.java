package com.qsoftware.forgemod.events;

import net.minecraft.item.Item;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.capability.EnergyStorageImplBase;
import com.qsoftware.forgemod.objects.item.BatteryItem;

@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID)
public class CommonEvents {
    @SubscribeEvent
    public static void onAttachItemCaps(AttachCapabilitiesEvent<Item> event) {
        if (event.getObject() instanceof BatteryItem) {
            event.addCapability(QForgeMod.getId("energy"), new EnergyStorageImplBase(500_000, 10_000, 10_000));
        }
    }
}
