package com.ultreon.randomthingz.world.gen.ores;

import com.ultreon.randomthingz.config.AbstractOreConfig;

@Deprecated
public abstract class AbstractOreConfigs {
    public AbstractOreConfig getConfig(ItemMaterialOre ore) {
//        return ore.getConfig().orElseThrow(() -> new IllegalArgumentException("Ore '" + ore.getName() + "' has no config."));
        return null;
    }
}
