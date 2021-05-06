package com.qtech.forgemod.item.tools.trait;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class WitherTrait extends AbstractPotionTrait {
    public WitherTrait() {

    }

    @Override
    public EffectInstance getEffectInstance() {
        return new EffectInstance(Effects.WITHER, 50, 1);
    }
}
