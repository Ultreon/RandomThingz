package com.qtech.randomthingz.block.base;

import com.qtech.randomthingz.commons.OreProperties;
import com.qtech.randomthingz.commons.interfaces.IHasOreProperties;
import net.minecraft.block.OreBlock;

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
