package com.qtech.randomthingz.item.base;

import com.qtech.randomthingz.commons.interfaces.IHasMaterialColor;
import net.minecraft.item.Item;

public abstract class MaterialColorizedItem extends Item implements IHasMaterialColor {
    public MaterialColorizedItem(Properties properties) {
        super(properties);
    }
}
