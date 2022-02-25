package com.ultreon.randomthingz.test;

import com.ultreon.randomthingz.test.network.Network;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TestMod.MOD_ID)
public class TestMod {
    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogManager.getLogger("RT:TestMod:Generic");
    public static final String MOD_ID = "test";

    public TestMod() {
        Network.initialize();
        TestModules.initialize();
    }

    public static ResourceLocation res(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
