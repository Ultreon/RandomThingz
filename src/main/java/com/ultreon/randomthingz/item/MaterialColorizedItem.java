package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.common.interfaces.MaterilColorHolder;
import net.minecraft.world.item.Item;

public abstract class MaterialColorizedItem extends Item implements MaterilColorHolder {
    public MaterialColorizedItem(Properties properties) {
        super(properties);
    }
}
