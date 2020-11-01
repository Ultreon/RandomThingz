package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.BlockInit;
import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

public class FletchingItemGroup extends ItemGroup {
    public static final FletchingItemGroup instance = new FletchingItemGroup(ItemGroup.GROUPS.length, "fletching_tab");

    public FletchingItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.BOW);
    }
}
