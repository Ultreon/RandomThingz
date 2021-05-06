package com.qtech.forgemod.modules.ui.groups;

import com.qtech.forgemod.item.common.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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
