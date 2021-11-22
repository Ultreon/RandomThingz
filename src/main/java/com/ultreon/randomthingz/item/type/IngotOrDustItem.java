package com.ultreon.randomthingz.item.type;

import com.ultreon.randomthingz.client.groups.MetalCraftablesItemGroup;
import com.ultreon.randomthingz.item.common.ModItemGroups;
import net.minecraft.item.Item;

/**
 * Item or dust item class.
 * Used for metal crafting items. Such as ingots or nuggets.
 * Has a default group: {@linkplain MetalCraftablesItemGroup} from the field {@linkplain ModItemGroups#METAL_CRAFTABLES}
 *
 * @author Qboi123
 */
public class IngotOrDustItem extends Item {
    public IngotOrDustItem() {
        super(new Item.Properties().group(ModItemGroups.METAL_CRAFTABLES));
    }
}
