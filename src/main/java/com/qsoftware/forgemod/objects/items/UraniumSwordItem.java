package com.qsoftware.forgemod.objects.items;

import com.qsoftware.forgemod.common.damagesource.DamageSourceInfinitySword;
import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Infinity sword item class.
 *
 * @author Qboi123
 */
public class UraniumSwordItem extends SwordItem {
    public UraniumSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
        super(tier, attackDamageIn, attackSpeedIn, new Properties().group(Groups.TOOLS));
    }

    @Override
    public boolean hasEffect(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean hitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, @NotNull LivingEntity player) {
        victim.addPotionEffect(new EffectInstance(Effects.POISON, ThreadLocalRandom.current().nextInt(50, 120), 2, true, false));
        return true;
    }
}
