package com.qsoftware.forgemod.objects.blocks.base;

import net.minecraft.block.WoodButtonBlock;

public class BaseWoodButtonBlock extends WoodButtonBlock {
    public BaseWoodButtonBlock(String name, Properties properties) {
        super(properties);
        setRegistryName(name);
    }
}
