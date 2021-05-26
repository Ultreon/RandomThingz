package com.qtech.randomthingz.item.type;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

/**
 * Base item class.
 *
 * @author Qboi123
 * @deprecated Use {@linkplain Item} instead.
 */
@Deprecated
public class BaseItem extends Item {
    public BaseItem(ItemGroup group) {
        super(new Item.Properties().group(group));
    }
}
