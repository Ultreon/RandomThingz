package com.qsoftware.forgemod.client.gui.hud;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.hud.pages.WindowPage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DebugPages {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        DebugScreen.registerPage(new WindowPage(QForgeMod.MOD_ID, "window"));
//        DebugScreen.registerPage(new PlayerPage1(QForgeMod.MOD_ID, "player1"));
    }
}
