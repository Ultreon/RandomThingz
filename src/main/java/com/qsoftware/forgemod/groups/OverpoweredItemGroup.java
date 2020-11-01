package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OverpoweredItemGroup extends ItemGroup {
    public static final OverpoweredItemGroup instance = new OverpoweredItemGroup(ItemGroup.GROUPS.length, "gems_tab");

    public OverpoweredItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.COMMAND_BLOCK);
    }
}
