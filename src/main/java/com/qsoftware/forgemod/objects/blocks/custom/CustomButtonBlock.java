package com.qsoftware.forgemod.objects.blocks.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.StoneButtonBlock;

public class CustomButtonBlock extends StoneButtonBlock {
    private final int activeDuration;

    public CustomButtonBlock(AbstractBlock.Properties properties, int activeDuration) {
        super(properties);
        this.activeDuration = activeDuration;
    }

    @Override
    public int getActiveDuration() {
        return activeDuration;
    }
}
