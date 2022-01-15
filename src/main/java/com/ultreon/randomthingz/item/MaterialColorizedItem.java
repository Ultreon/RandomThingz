package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.common.interfaces.IHasMaterialColor;
import net.minecraft.world.item.Item;

public abstract class MaterialColorizedItem extends Item implements IHasMaterialColor {
    public MaterialColorizedItem(Properties properties) {
        super(properties);
    }
}
