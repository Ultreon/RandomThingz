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

    @Override
    public void fill(NonNullList<ItemStack> items) {
//        super.fill(items);
        items.add(new ItemStack(ModItems.CHEESE_BURGER.get()));
        items.add(new ItemStack(ModItems.CHEESE_SLICE.get()));
        items.add(new ItemStack(ModItems.CHEESE.get()));
        items.add(new ItemStack(ModItems.EUCALYPTUS_LEAF.get()));
        items.add(new ItemStack(Items.RABBIT));
        items.add(new ItemStack(Items.COOKED_RABBIT));
        items.add(new ItemStack(ModItems.CHICKEN_LEG.get()));
        items.add(new ItemStack(ModItems.COOKED_CHICKEN_LEG.get()));
        items.add(new ItemStack(ModItems.MEATBALL.get()));
        items.add(new ItemStack(ModItems.COOKED_MEATBALL.get()));
        items.add(new ItemStack(ModItems.SHOARMA.get()));
        items.add(new ItemStack(ModItems.COOKED_SHOARMA.get()));
        items.add(new ItemStack(ModItems.PORK_SHANK.get()));
        items.add(new ItemStack(ModItems.COOKED_PORK_SHANK.get()));
        items.add(new ItemStack(ModItems.TOMAHAWK.get()));
        items.add(new ItemStack(ModItems.COOKED_TOMAHAWK.get()));
        items.add(new ItemStack(Items.CHICKEN));
        items.add(new ItemStack(Items.COOKED_CHICKEN));
        items.add(new ItemStack(Items.MUTTON));
        items.add(new ItemStack(Items.COOKED_MUTTON));
        items.add(new ItemStack(Items.BEEF));
        items.add(new ItemStack(Items.COOKED_BEEF));
        items.add(new ItemStack(Items.PORKCHOP));
        items.add(new ItemStack(Items.COOKED_PORKCHOP));
        items.add(new ItemStack(Items.COD));
        items.add(new ItemStack(Items.COOKED_COD));
        items.add(new ItemStack(Items.SALMON));
        items.add(new ItemStack(Items.COOKED_SALMON));
        items.add(new ItemStack(Items.APPLE));
        items.add(new ItemStack(ModItems.BAKED_APPLE.get()));
        items.add(new ItemStack(Items.GOLDEN_APPLE));
        items.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
        items.add(new ItemStack(Items.CARROT));
        items.add(new ItemStack(ModItems.COOKED_CARROT.get()));
        items.add(new ItemStack(Items.GOLDEN_CARROT));
        items.add(new ItemStack(Items.POTATO));
        items.add(new ItemStack(Items.BAKED_POTATO));
        items.add(new ItemStack(Items.PUMPKIN_PIE));
        items.add(new ItemStack(Items.CAKE));
        items.add(new ItemStack(Items.MILK_BUCKET));
        items.add(new ItemStack(Items.HONEY_BOTTLE));
    }
}
