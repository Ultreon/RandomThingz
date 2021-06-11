package com.qtech.randomthingz.event;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.capability.EnergyStorageImplBase;
import com.qtech.randomthingz.item.BatteryItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class CommonEvents {
    @SubscribeEvent
    public static void onAttachItemCaps(AttachCapabilitiesEvent<Item> event) {
        if (event.getObject() instanceof BatteryItem) {
            event.addCapability(RandomThingz.rl("energy"), new EnergyStorageImplBase(500_000, 10_000, 10_000));
        }
    }
}
