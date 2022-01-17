package com.ultreon.randomthingz.client.debug.menu;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.debug.menu.pages.WindowPage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DebugPages {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        DebugMenu.registerPage(new WindowPage(RandomThingz.MOD_ID, "window"));
//        DebugScreen.registerPage(new PlayerPage1(RandomThingz.MOD_ID, "player1"));
    }
}