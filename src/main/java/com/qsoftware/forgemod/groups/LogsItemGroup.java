package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.BlockInit;
import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LogsItemGroup extends ItemGroup {
    public static final LogsItemGroup instance = new LogsItemGroup(ItemGroup.GROUPS.length, "logs_tab");

    public LogsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.OAK_LOG);
    }
}
