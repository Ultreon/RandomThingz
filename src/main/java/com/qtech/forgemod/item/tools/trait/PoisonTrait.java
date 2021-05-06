package com.qtech.forgemod.item.tools.trait;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class PoisonTrait extends AbstractPotionTrait {
    public PoisonTrait() {

    }

    @Override
    public EffectInstance getEffectInstance() {
        return new EffectInstance(Effects.POISON, 50, 1);
    }
}
