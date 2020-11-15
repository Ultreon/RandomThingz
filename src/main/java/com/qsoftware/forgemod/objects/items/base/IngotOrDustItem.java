package com.qsoftware.forgemod.objects.items.base;

import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.groups.MetalCraftablesItemGroup;
import net.minecraft.item.Item;

/**
 * Item or dust item class.
 * Used for metal crafting items. Such as ingots or nuggets.
 * Has a default group: {@link MetalCraftablesItemGroup} from the field {@link Groups#METAL_CRAFTABLES}
 *
 * @author Qboi123
 */
public class IngotOrDustItem extends Item {
    public IngotOrDustItem() {
        super(new Item.Properties().group(Groups.METAL_CRAFTABLES));
    }
}
