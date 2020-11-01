package com.qsoftware.forgemod.objects.items.base;

import com.qsoftware.forgemod.groups.Groups;
import net.minecraft.item.Item;

public class IngredientItem extends Item {
    public IngredientItem(String name) {
        super(new Properties().group(Groups.INGOTS_OR_DUSTS));
        this.setRegistryName(name);
    }
}
