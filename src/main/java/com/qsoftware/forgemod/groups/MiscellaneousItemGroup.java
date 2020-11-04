package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MiscellaneousItemGroup extends ItemGroup {
    public static final MiscellaneousItemGroup instance = new MiscellaneousItemGroup(ItemGroup.GROUPS.length, "qforgemod_misc");

    public MiscellaneousItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ItemInit.LEGENDARY_ENDER_PEARL);
    }
}
