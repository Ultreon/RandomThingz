package com.qsoftware.forgemod.modules.ui.groups;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * OP item group.
 *
 * @author Qboi123
 */
public class OverpoweredItemGroup extends ItemGroup {
    public static final OverpoweredItemGroup instance = new OverpoweredItemGroup(ItemGroup.GROUPS.length, "qforgemod_god");

    public OverpoweredItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.COMMAND_BLOCK);
    }
}
