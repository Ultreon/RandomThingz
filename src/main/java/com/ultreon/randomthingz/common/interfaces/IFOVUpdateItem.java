package com.ultreon.randomthingz.common.interfaces;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Update item for FOV (Field of View).
 *
 * @author Qboi123
 */

/**
 * Implemented on Items which update/alter FOV under certain conditions.
 */
public interface IFOVUpdateItem {
    float getFOVMod(ItemStack stack, Player player);
}