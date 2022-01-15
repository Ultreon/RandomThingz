package com.ultreon.randomthingz.block.custom;

import net.minecraft.world.level.block.StoneButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CustomButtonBlock extends StoneButtonBlock {
    private final int activeDuration;

    public CustomButtonBlock(BlockBehaviour.Properties properties, int activeDuration) {
        super(properties);
        this.activeDuration = activeDuration;
    }

    @Override
    public int getPressDuration() {
        return activeDuration;
    }
}
