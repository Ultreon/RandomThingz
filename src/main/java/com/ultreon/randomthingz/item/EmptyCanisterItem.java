package com.ultreon.randomthingz.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class EmptyCanisterItem extends CanisterItem {
    public EmptyCanisterItem(Properties properties) {
        super(properties);
    }

    @Override
    public FluidStack getFluid(ItemStack stack) {
        return FluidStack.EMPTY;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (allowdedIn(group)) {
            items.add(new ItemStack(this));
        }
    }
}
