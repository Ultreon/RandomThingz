package com.ultreon.randomthingz.commons.interfaces;

import net.minecraft.item.ItemStack;

/**
 * Sliceable items interface.
 *
 * @author Qboi123
 */
public interface Sliceable {
    ItemStack onKnifeSlice(ItemStack stack);
}
