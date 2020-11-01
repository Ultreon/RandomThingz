package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NatureItemGroup extends ItemGroup {
    public static final NatureItemGroup instance = new NatureItemGroup(ItemGroup.GROUPS.length, "nature_tab");

    public NatureItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ItemInit.EUCALYPTUS_LEAF);
    }
}
