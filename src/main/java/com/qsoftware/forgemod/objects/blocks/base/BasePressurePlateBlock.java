package com.qsoftware.forgemod.objects.blocks.base;

import net.minecraft.block.PressurePlateBlock;

public class BasePressurePlateBlock extends PressurePlateBlock {
    public BasePressurePlateBlock(String name, Sensitivity sensitivity, Properties properties) {
        super(sensitivity, properties);
        setRegistryName(name);
    }
}
