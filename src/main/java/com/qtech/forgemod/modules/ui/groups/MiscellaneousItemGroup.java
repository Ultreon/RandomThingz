package com.qtech.forgemod.modules.ui.groups;

import com.qtech.forgemod.modules.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Miscellaneous item group.
 *
 * @author Qboi123
 */
public class MiscellaneousItemGroup extends ItemGroup {
    public static final MiscellaneousItemGroup instance = new MiscellaneousItemGroup(ItemGroup.GROUPS.length, "qforgemod_misc");

    public MiscellaneousItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ModItems.LEGENDARY_ENDER_PEARL.get());
    }
}
