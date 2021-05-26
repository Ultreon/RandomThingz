package com.qtech.randomthingz.commons.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * Update item for FOV (Field of View).
 *
 * @author Qboi123
 */

/**
 * Implemented on Items which update/alter FOV under certain conditions.
 */
public interface IFOVUpdateItem {
    float getFOVMod(ItemStack stack, PlayerEntity player);
}