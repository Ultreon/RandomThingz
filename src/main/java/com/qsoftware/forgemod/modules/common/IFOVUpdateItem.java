package com.qsoftware.forgemod.modules.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * Implemented on Items which update/alter FOV under certain conditions.
 */
public interface IFOVUpdateItem {
	float getFOVMod(ItemStack stack, PlayerEntity player);
}