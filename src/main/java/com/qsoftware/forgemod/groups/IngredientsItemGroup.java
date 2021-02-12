package com.qsoftware.forgemod.groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Ingredients item group.
 *
 * @author Qboi123
 */
public class IngredientsItemGroup extends ItemGroup {
    public static final IngredientsItemGroup instance = new IngredientsItemGroup(ItemGroup.GROUPS.length, "qforgemod_ingredients");

    public IngredientsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.STRING);
    }
}
