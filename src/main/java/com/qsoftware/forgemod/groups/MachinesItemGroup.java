package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.BlockInit;
import com.qsoftware.forgemod.init.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Machines item group.
 * Used for blocks that have tile entities or items that have an GUI.
 *
 * @author Qboi123
 */
public class MachinesItemGroup extends ItemGroup {
    public static final MachinesItemGroup instance = new MachinesItemGroup(ItemGroup.GROUPS.length, "qforgemod_machines");

    public MachinesItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ModBlocks.COAL_GENERATOR.get());
    }
}
