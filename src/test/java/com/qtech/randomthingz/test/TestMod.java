package com.qtech.randomthingz.test;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.registration.Registration;
import com.qtech.randomthingz.test.item.DebugItem;
import com.qtech.randomthingz.test.network.Network;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TestMod.MOD_ID)
public class TestMod {
    public static final Logger LOGGER = LogManager.getLogger("RT:TestMod:Generic");
    public static final String MOD_ID = "test";

    public TestMod() {
        if (RandomThingz.isModDev(false)) {
            Registration.ITEMS.register("debug_item", DebugItem::new);
            Network.initialize();
        } else {

        }
    }

    public void onLoad(FMLClientSetupEvent event) {
        event.
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
