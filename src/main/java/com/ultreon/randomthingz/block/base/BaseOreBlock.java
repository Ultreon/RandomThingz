package com.ultreon.randomthingz.block.base;

import com.ultreon.randomthingz.common.OreProperties;
import com.ultreon.randomthingz.common.interfaces.IHasOreProperties;
import net.minecraft.world.level.block.OreBlock;

@Deprecated
public class BaseOreBlock extends OreBlock implements IHasOreProperties {
    @Deprecated
    private final OreProperties oreProperties;

    @Deprecated
    public BaseOreBlock(Properties properties, OreProperties oreProperties) {
        super(properties);
        this.oreProperties = oreProperties;
    }

    @Deprecated
    @Override
    public OreProperties getOreProperties() {
        return oreProperties;
    }
}
