package com.qtech.randomthingz.item.tools.trait;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.Color;

public class WitherTrait extends AbstractPotionTrait {
    public WitherTrait() {

    }

    @Override
    public Color getColor() {
        return Color.fromHex("#3F3F3F");
    }

    @Override
    public EffectInstance getEffectInstance() {
        return new EffectInstance(Effects.WITHER, 50, 1);
    }
}
