package com.qsoftware.forgemod.modules.items.objects.base;

import com.qsoftware.forgemod.common.interfaces.IHasDyeColor;
import net.minecraft.item.Item;

public abstract class DyeColorizedItem extends Item implements IHasDyeColor {
    public DyeColorizedItem(Properties properties) {
        super(properties);
    }
}
