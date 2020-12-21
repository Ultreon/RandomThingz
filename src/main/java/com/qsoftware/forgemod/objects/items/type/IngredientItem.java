package com.qsoftware.forgemod.objects.items.type;

import com.qsoftware.forgemod.init.ModItemGroups;
import net.minecraft.item.Item;

/**
 * Game PC block class.
 *
 * @author Qboi123
 */
public class IngredientItem extends Item {
    public IngredientItem() {
        super(new Properties().group(ModItemGroups.INGREDIENTS));
    }
}
