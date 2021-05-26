package com.qtech.randomthingz.item.tools;

import com.qtech.randomthingz.RandomThingz;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Infinity sword item class.
 *
 * @author Qboi123
 */
@Deprecated
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class UraniumAxeItem extends AxeItem {
    public UraniumAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean hitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, @NotNull LivingEntity player) {
        victim.addPotionEffect(new EffectInstance(Effects.WITHER, ThreadLocalRandom.current().nextInt(50, 120), 2, true, false));
        return true;
    }
}
