package com.qtech.forgemod.world.gen.ores;

import com.qtech.forgemod.config.AbstractOreConfig;

@Deprecated
public abstract class AbstractOreConfigs {
    public AbstractOreConfig getConfig(DefaultOre ore) {
//        return ore.getConfig().orElseThrow(() -> new IllegalArgumentException("Ore '" + ore.getName() + "' has no config."));
        return null;
    }
}
