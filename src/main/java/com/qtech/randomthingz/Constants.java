package com.qtech.randomthingz;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Logger;

/**
 * Constants class.
 * @deprecated everything is already in {@link RandomThingz} class or in {@link Minecraft} class.
 */
@Deprecated
public class Constants {
    @Deprecated
    static IProxy proxy;
    @Deprecated
    static RandomThingz randomthingz;
    @Deprecated
    static Logger logger;

    /**
     * @deprecated use {@link RandomThingz#LOGGER} instead.
     * @return the Random Thingz generic logger.
     */
    @Deprecated
    public static Logger getLogger() {
        return logger;
    }

    /**
     * @deprecated use {@link RandomThingz#getInstance()} instead.
     * @return the Random Thingz mod instance.
     */
    @Deprecated
    public static RandomThingz getRandomThingzMod() {
        return randomthingz;
    }

    /**
     * Get Minecraft instance.
     *
     * @deprecated use {@link Minecraft#getInstance()}, or a minecraft instance field / variable.
     * @return the minecraft instance.
     */
    @Deprecated
    @OnlyIn(Dist.CLIENT)
    public static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    /**
     * @deprecated  Use {@link RandomThingz#getProxy()}
     * @return the Random Thingz proxy class.
     */
    @Deprecated
    public static IProxy getProxy() {
        return proxy;
    }

    /**
     * @deprecated use {@link RandomThingz#isModDev()} or {@link RandomThingz#isModDev(boolean)} instead.
     * @return true if Minecraft Forge is loaded in Mod Development state, false if it's loaded normal from a launcher.
     */
    @Deprecated
    public static boolean isModDev() {
        return Minecraft.getInstance().getVersion().equals("MOD_DEV");
    }
}
