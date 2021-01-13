package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

/**
 * Food item group.
 *
 * @author Qboi123
 */
public class FoodItemGroup extends ItemGroup {
    public static final FoodItemGroup instance = new FoodItemGroup(ItemGroup.GROUPS.length, "qforgemod_food");

    public FoodItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ModItems.CHEESE_BURGER.get());
    }
}
