package com.ultreon.randomthingz.block.base;

import net.minecraft.world.level.block.PressurePlateBlock;

/**
 * Game PC block class.
 *
 * @author Qboi123
 * @deprecated Use {@linkplain PressurePlateBlock} instead.
 */
@Deprecated
public class BasePressurePlateBlock extends PressurePlateBlock {
    public BasePressurePlateBlock(Sensitivity sensitivity, Properties properties) {
        super(sensitivity, properties);
    }
}
