package com.qtech.forgemod.modules.ui.groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Tools item group.
 * For generic tools.
 *
 * @author Qboi123
 */
public class ToolsItemGroup extends ItemGroup {
    public static final ToolsItemGroup instance = new ToolsItemGroup(ItemGroup.GROUPS.length, "qforgemod_tools");

    public ToolsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.IRON_AXE);
    }
}
