package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.BlockInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MachinesItemGroup extends ItemGroup {
    public static final MachinesItemGroup instance = new MachinesItemGroup(ItemGroup.GROUPS.length, "qforgemod_machines");

    public MachinesItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(BlockInit.QUARRY_BLOCK.get());
    }
}
