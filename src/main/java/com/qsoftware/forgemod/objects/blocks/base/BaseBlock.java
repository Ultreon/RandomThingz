package com.qsoftware.forgemod.objects.blocks.base;

import net.minecraft.block.Block;

public class BaseBlock extends Block {
    public BaseBlock(String name, Properties properties) {
        super(properties);
        this.setRegistryName(name);
    }
}
