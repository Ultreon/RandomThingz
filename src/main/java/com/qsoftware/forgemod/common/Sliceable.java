package com.qsoftware.forgemod.common;

import net.minecraft.item.ItemStack;

/**
 * Sliceable items interface.
 *
 * @author Qboi123
 */
public interface Sliceable {
    ItemStack onKnifeSlice(ItemStack stack);
}
