package com.ultreon.randomthingz;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Logger;

/**
 * Constants class.
 *
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
     * @return the Random Thingz generic logger.
     * @deprecated use {@link RandomThingz#LOGGER} instead.
     */
    @Deprecated
    public static Logger getLogger() {
        return logger;
    }

    /**
     * @return the Random Thingz mod instance.
     * @deprecated use {@link RandomThingz#getInstance()} instead.
     */
    @Deprecated
    public static RandomThingz getRandomThingzMod() {
        return randomthingz;
    }

    /**
     * Get Minecraft instance.
     *
     * @return the minecraft instance.
     * @deprecated use {@link Minecraft#getInstance()}, or a minecraft instance field / variable.
     */
    @Deprecated
    @OnlyIn(Dist.CLIENT)
    public static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    /**
     * @return the Random Thingz proxy class.
     * @deprecated Use {@link RandomThingz#getProxy()}
     */
    @Deprecated
    public static IProxy getProxy() {
        return proxy;
    }

    /**
     * @return true if Minecraft Forge is loaded in Mod Development state, false if it's loaded normal from a launcher.
     * @deprecated use {@link RandomThingz#isModDev()} or {@link RandomThingz#isModDev(boolean)} instead.
     */
    @Deprecated
    public static boolean isModDev() {
        return Minecraft.getInstance().getLaunchedVersion().equals("MOD_DEV");
    }
}
