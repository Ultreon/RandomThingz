package com.qsoftware.forgemod.objects.items.base;

import com.qsoftware.forgemod.groups.Groups;
import net.minecraft.item.Item;

public class IngredientItem extends Item {
    public IngredientItem() {
        super(new Properties().group(Groups.INGREDIENTS));
    }
}
