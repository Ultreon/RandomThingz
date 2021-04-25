package com.qtech.forgemod.events;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.capability.EnergyStorageImplBase;
import com.qtech.forgemod.modules.items.objects.energy.BatteryItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QForgeMod.modId)
public class CommonEvents {
    @SubscribeEvent
    public static void onAttachItemCaps(AttachCapabilitiesEvent<Item> event) {
        if (event.getObject() instanceof BatteryItem) {
            event.addCapability(QForgeMod.rl("energy"), new EnergyStorageImplBase(500_000, 10_000, 10_000));
        }
    }
}
