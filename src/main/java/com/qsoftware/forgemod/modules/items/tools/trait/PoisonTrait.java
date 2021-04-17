package com.qsoftware.forgemod.modules.items.tools.trait;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import org.jetbrains.annotations.NotNull;

public class PoisonTrait extends AbstractPotionTrait {
    public PoisonTrait() {

    }

    @Override
    public EffectInstance getEffectInstance() {
        return new EffectInstance(Effects.POISON, 50, 2);
    }
}
