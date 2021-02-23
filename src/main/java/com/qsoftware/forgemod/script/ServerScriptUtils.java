package com.qsoftware.forgemod.script;

import com.qsoftware.forgemod.QForgeMod;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = QForgeMod.modId)
public class ServerScriptUtils {
    private static MinecraftServer server;

    public static MinecraftServer getServer() {
        return server;
    }

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
        server = event.getServer();
    }
}
