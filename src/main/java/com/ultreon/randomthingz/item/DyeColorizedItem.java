package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.common.interfaces.IHasDyeColor;
import net.minecraft.item.Item;

public abstract class DyeColorizedItem extends Item implements IHasDyeColor {
    public DyeColorizedItem(Properties properties) {
        super(properties);
    }
}
