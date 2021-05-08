package com.qtech.randomthingz.modules.ui.groups;

import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Food item group.
 *
 * @author Qboi123
 */
public class FluidItemGroup extends ItemGroup {
    public static final FluidItemGroup instance = new FluidItemGroup(ItemGroup.GROUPS.length, "randomthingz_fluids");

    public FluidItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Fluids.WATER.getFilledBucket());
    }
}
