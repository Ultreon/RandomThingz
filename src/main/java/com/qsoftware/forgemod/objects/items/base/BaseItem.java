package com.qsoftware.forgemod.objects.items.base;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BaseItem extends Item {
    public BaseItem(String name, ItemGroup group) {
        super(new Item.Properties().group(group));
        this.setRegistryName(name);
    }
}
