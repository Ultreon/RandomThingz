package com.qtech.forgemod.modules.items.objects;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Map;

public class SilverItem extends Item {
    public SilverItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasEnchantmentGlint(ItemStack stack) {
        boolean hasSmite = false;
        boolean hasMultiple = false;
        int enchantmentAmount = 0;
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);

        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            if (entry.getKey() == Enchantments.SMITE && entry.getValue() > 1) {
                hasSmite = true;
            }
            if (entry.getValue() != 0) {
                enchantmentAmount++;
                if (enchantmentAmount > 1) {
                    hasMultiple = true;
                }
            }
        }

        if (hasSmite) {
            if (hasMultiple) {
                return stack.isEnchanted();
            } else {
                return false;
            }
        }
        return stack.isEnchanted();
    }

    @Override
    public void whenCreated(ItemStack stack, World dimensionIn, PlayerEntity playerIn) {
        stack.addEnchantment(Enchantments.SMITE, 2);
    }

    @Override
    public void inventoryTick(ItemStack stack, World dimensionIn, Entity entityIn, int itemSlot, boolean isSelected) {
        stack.addEnchantment(Enchantments.SMITE, 2);
    }
}
