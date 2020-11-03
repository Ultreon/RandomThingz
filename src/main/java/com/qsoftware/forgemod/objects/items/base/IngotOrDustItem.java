package com.qsoftware.forgemod.objects.items.base;

import com.qsoftware.forgemod.groups.Groups;
import net.minecraft.item.Item;

public class IngotOrDustItem extends Item {
    public IngotOrDustItem() {
        super(new Item.Properties().group(Groups.INGOTS_OR_DUSTS));
    }
}
