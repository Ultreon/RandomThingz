package com.qsoftware.forgemod.common;

import net.minecraft.item.ItemStack;

public interface Sliceable {
    ItemStack onKnifeSlice(ItemStack stack);
}
