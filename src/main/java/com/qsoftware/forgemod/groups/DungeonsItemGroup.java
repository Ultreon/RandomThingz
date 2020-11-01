package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.BlockInit;
import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DungeonsItemGroup extends ItemGroup {
    public static final DungeonsItemGroup instance = new DungeonsItemGroup(ItemGroup.GROUPS.length, "dungeons_tab");

    public DungeonsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ItemInit.DUNGEONS);
    }
}
