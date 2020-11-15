package com.qsoftware.forgemod.objects.items.base;

import com.qsoftware.forgemod.common.IHasDyeColor;
import net.minecraft.item.Item;

public abstract class DyeColorizedItem extends Item implements IHasDyeColor {
    public DyeColorizedItem(Properties properties) {
        super(properties);
    }
}
