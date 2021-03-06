package com.ultreon.texturedmodels.block;

import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

/**
 * Nothing important to see here, visit {@linkplain FrameBlock} for a better documentation
 *
 * @author PianoManu
 * @version 1.0 08/29/20
 */
public class SlopeFrameBlock extends StairsFrameBlock {
    public SlopeFrameBlock(Supplier<BlockState> state, Properties properties) {
        super(state, properties);
    }
}
