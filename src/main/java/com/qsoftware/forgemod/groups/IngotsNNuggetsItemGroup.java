package com.qsoftware.forgemod.groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

public class IngotsNNuggetsItemGroup extends ItemGroup {
    public static final IngotsNNuggetsItemGroup instance = new IngotsNNuggetsItemGroup(ItemGroup.GROUPS.length, "qforgemod_ingots_n_nuggets");

    public IngotsNNuggetsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.IRON_INGOT);
    }
}
