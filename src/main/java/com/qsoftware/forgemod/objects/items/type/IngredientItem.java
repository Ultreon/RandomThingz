package com.qsoftware.forgemod.objects.items.type;

import com.qsoftware.forgemod.groups.Groups;
import net.minecraft.item.Item;

/**
 * Game PC block class.
 *
 * @author Qboi123
 */
public class IngredientItem extends Item {
    public IngredientItem() {
        super(new Properties().group(Groups.INGREDIENTS));
    }
}
