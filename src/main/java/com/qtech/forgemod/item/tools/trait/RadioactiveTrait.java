package com.qtech.forgemod.item.tools.trait;

import com.qtech.forgemod.effect.common.ModEffects;
import net.minecraft.potion.EffectInstance;

public class RadioactiveTrait extends AbstractPotionTrait {
    @Override
    public EffectInstance getEffectInstance() {
        return new EffectInstance(ModEffects.RADIATION.get(), 1200, 5);
    }
}
