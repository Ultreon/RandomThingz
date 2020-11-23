package com.qsoftware.forgemod.groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
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

    @Override
    public void fill(NonNullList<ItemStack> items) {
        super.fill(items);
        items.add(new ItemStack(Items.STICK));
        items.add(new ItemStack(Items.STRING));
        items.add(new ItemStack(Items.BLAZE_ROD));
        items.add(new ItemStack(Items.BLAZE_POWDER));
        items.add(new ItemStack(Items.BONE));
        items.add(new ItemStack(Items.BONE_MEAL));
        items.add(new ItemStack(Items.BOOK));
        items.add(new ItemStack(Items.BOWL));
        items.add(new ItemStack(Items.BRICK));
        items.add(new ItemStack(Items.BRICK));
        items.add(new ItemStack(Items.BUCKET));
        items.add(new ItemStack(Items.CLAY_BALL));
        items.add(new ItemStack(Items.CARROT_ON_A_STICK));
        items.add(new ItemStack(Items.WARPED_FUNGUS_ON_A_STICK));
    }
}
