package com.ultreon.randomthingz.block.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import org.jetbrains.annotations.NotNull;

public class ModButtonBlock extends AbstractButtonBlock {
    private final int activeDuration;

    public ModButtonBlock(Properties properties, int activeDuration) {
        this(properties, false, activeDuration);
    }

    public ModButtonBlock(AbstractBlock.Properties properties, boolean isWooden, int activeDuration) {
        super(isWooden, properties);
        this.activeDuration = activeDuration;
    }

    @Override
    public int getActiveDuration() {
        return activeDuration;
    }

    @Override
    protected @NotNull SoundEvent getSoundEvent(boolean isOn) {
        return isOn ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }
}
