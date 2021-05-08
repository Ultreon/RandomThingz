package com.qtech.randomthingz.item.tools;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Infinity sword item class.
 *
 * @author Qboi123
 */
@Deprecated
public class UraniumPickaxeItem extends PickaxeItem {
    public UraniumPickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean hitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, @NotNull LivingEntity player) {
        victim.addPotionEffect(new EffectInstance(Effects.WITHER, ThreadLocalRandom.current().nextInt(50, 120), 2, true, false));
        return true;
    }
}
