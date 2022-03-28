package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.common.interfaces.DyeColorHolder;
import net.minecraft.world.item.Item;

public abstract class DyeColorizedItem extends Item implements DyeColorHolder {
    public DyeColorizedItem(Properties properties) {
        super(properties);
    }
}
