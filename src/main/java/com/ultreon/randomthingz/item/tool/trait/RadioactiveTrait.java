package com.ultreon.randomthingz.item.tool.trait;

import com.ultreon.randomthingz.effect.common.ModEffects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.Color;

public class RadioactiveTrait extends AbstractPotionTrait {
    @Override
    public Color getColor() {
        return Color.fromHex("#009F00");
    }

    @Override
    public EffectInstance getEffectInstance() {
        return new EffectInstance(ModEffects.RADIATION.get(), 1200, 5);
    }
}
