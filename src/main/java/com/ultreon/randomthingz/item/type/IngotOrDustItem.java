package com.ultreon.randomthingz.item.type;

import com.ultreon.randomthingz.init.ModCreativeTabs;
import com.ultreon.randomthingz.item.creativetab.MetalCraftablesTab;
import net.minecraft.world.item.Item;

/**
 * Item or dust item class.
 * Used for metal crafting items. Such as ingots or nuggets.
 * Has a default group: {@linkplain MetalCraftablesTab} from the field {@linkplain ModCreativeTabs#METAL_CRAFTABLES}
 *
 * @author Qboi123
 */
public class IngotOrDustItem extends Item {
    public IngotOrDustItem() {
        super(new Item.Properties().tab(ModCreativeTabs.METAL_CRAFTABLES));
    }
}
