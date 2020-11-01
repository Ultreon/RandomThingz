package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

public class ToolsItemGroup extends ItemGroup {
    public static final ToolsItemGroup instance = new ToolsItemGroup(ItemGroup.GROUPS.length, "tools_tab");

    public ToolsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.IRON_AXE);
    }
}
