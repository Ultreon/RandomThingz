package com.qtech.randomthingz.test;

import com.qtech.randomthingz.test.network.Network;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TestMod.MOD_ID)
public class TestMod {
    public static final Logger LOGGER = LogManager.getLogger("RT:TestMod:Generic");
    public static final String MOD_ID = "test";

    public TestMod() {
        Network.initialize();
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
