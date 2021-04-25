package com.qtech.forgemod.modules.environment.ores;

import com.qtech.forgemod.modules.environment.ores.ModOreGen;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class OresInitializer {
    @Getter
    private final ModOreGen oreGen;

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        oreGen.addOresFeatures();
    }
}
