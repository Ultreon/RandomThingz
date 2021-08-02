package com.ultreon.randomthingz.item.tools.trait;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.Color;

public class ParalyzeTrait extends AbstractPotionTrait {
    public ParalyzeTrait() {

    }

    @Override
    public Color getColor() {
        return Color.fromHex("#fff000");
    }

    @Override
    public EffectInstance getEffectInstance() {
        return new EffectInstance(Effects.SLOWNESS, 30, 29);
    }
}
