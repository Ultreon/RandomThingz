package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.BlockInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OresItemGroup extends ItemGroup {
    public static final OresItemGroup instance = new OresItemGroup(ItemGroup.GROUPS.length, "qforgemod_ores");

    public OresItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(BlockInit.RUBY_ORE.get());
    }
}
