package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.commons.interfaces.IHasMaterialColor;
import net.minecraft.item.Item;

public abstract class MaterialColorizedItem extends Item implements IHasMaterialColor {
    public MaterialColorizedItem(Properties properties) {
        super(properties);
    }
}
