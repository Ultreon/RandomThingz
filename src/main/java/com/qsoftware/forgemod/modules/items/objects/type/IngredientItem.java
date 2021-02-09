package com.qsoftware.forgemod.modules.items.objects.type;

import com.qsoftware.forgemod.modules.ui.ModItemGroups;
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
