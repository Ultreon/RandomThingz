package com.ultreon.texturedmodels.util;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * Some properties needed for frame blocks
 *
 * @author PianoManu
 * @version 1.1 09/17/20
 */
public class BCBlockStateProperties {
    /**
     * Whether a frame block holds a block
     */
    public static final BooleanProperty CONTAINS_BLOCK = BooleanProperty.create("contains_block");

    /**
     * Whether a block emits light
     * 0  : no light
     * ...
     * 15 : full light
     */
    public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);
}
