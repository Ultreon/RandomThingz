package com.ultreon.randomthingz.common.interfaces;

import net.minecraft.world.item.ItemStack;

/**
 * Sliceable items interface.
 *
 * @author Qboi123
 */
public interface Sliceable {
    ItemStack onKnifeSlice(ItemStack stack);
}
