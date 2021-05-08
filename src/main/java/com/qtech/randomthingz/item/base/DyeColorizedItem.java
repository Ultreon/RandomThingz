package com.qtech.randomthingz.item.base;

import com.qtech.randomthingz.commons.interfaces.IHasDyeColor;
import net.minecraft.item.Item;

public abstract class DyeColorizedItem extends Item implements IHasDyeColor {
    public DyeColorizedItem(Properties properties) {
        super(properties);
    }
}
