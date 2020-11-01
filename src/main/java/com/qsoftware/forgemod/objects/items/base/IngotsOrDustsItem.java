package com.qsoftware.forgemod.objects.items.base;

import com.qsoftware.forgemod.groups.Groups;
import net.minecraft.item.Item;

public class IngotsOrDustsItem extends Item {
    public IngotsOrDustsItem(String name) {
        super(new Item.Properties().group(Groups.INGOTS_OR_DUSTS));
        this.setRegistryName(name);
    }
}
