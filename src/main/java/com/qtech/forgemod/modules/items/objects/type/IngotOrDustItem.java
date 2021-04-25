package com.qtech.forgemod.modules.items.objects.type;

import com.qtech.forgemod.groups.MetalCraftablesItemGroup;
import com.qtech.forgemod.modules.ui.ModItemGroups;
import net.minecraft.item.Item;

/**
 * Item or dust item class.
 * Used for metal crafting items. Such as ingots or nuggets.
 * Has a default group: {@link MetalCraftablesItemGroup} from the field {@link ModItemGroups#METAL_CRAFTABLES}
 *
 * @author Qboi123
 */
public class IngotOrDustItem extends Item {
    public IngotOrDustItem() {
        super(new Item.Properties().group(ModItemGroups.METAL_CRAFTABLES));
    }
}
