package com.qtech.randomthingz;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Logger;

public class Constants {
    static IProxy proxy;
    static RandomThingz randomthingz;
    static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    public static RandomThingz getRandomThingzMod() {
        return randomthingz;
    }

    @OnlyIn(Dist.CLIENT)
    public static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    public static IProxy getProxy() {
        return proxy;
    }

    public static boolean isModDev() {
        return Minecraft.getInstance().getVersion().equals("MOD_DEV");
    }
}
