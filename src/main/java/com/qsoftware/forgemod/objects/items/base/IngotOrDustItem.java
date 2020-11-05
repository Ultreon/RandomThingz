package com.qsoftware.forgemod.objects.items.base;

import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.groups.IngotsNNuggetsItemGroup;
import net.minecraft.item.Item;

/**
 * Item or dust item class.
 * Used for metal crafting items. Such as ingots or nuggets.
 * Has a default group: {@link IngotsNNuggetsItemGroup} from the field {@link Groups#INGOTS_OR_DUSTS}
 *
 * @author Qboi123
 */
public class IngotOrDustItem extends Item {
    public IngotOrDustItem() {
        super(new Item.Properties().group(Groups.INGOTS_OR_DUSTS));
    }
}
