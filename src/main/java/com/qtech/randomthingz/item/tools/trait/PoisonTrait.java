package com.qtech.randomthingz.item.tools.trait;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.Color;

public class PoisonTrait extends AbstractPotionTrait {
    public PoisonTrait() {

    }

    @Override
    public Color getColor() {
        return Color.fromHex("#007F3F");
    }

    @Override
    public EffectInstance getEffectInstance() {
        return new EffectInstance(Effects.POISON, 50, 1);
    }
}
