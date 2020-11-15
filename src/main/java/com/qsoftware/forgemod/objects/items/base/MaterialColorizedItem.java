package com.qsoftware.forgemod.objects.items.base;

import com.qsoftware.forgemod.common.IHasMaterialColor;
import net.minecraft.item.Item;

public abstract class MaterialColorizedItem extends Item implements IHasMaterialColor {
    public MaterialColorizedItem(Properties properties) {
        super(properties);
    }
}
