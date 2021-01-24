package com.qsoftware.forgemod.modules.ores;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class OresInitializer {
    private final ModOreGen oreGen;

    OresInitializer(ModOreGen oreGen) {
        this.oreGen = oreGen;
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        oreGen.addOresFeatures();
    }

    public ModOreGen getOreGen() {
        return oreGen;
    }
}
