package com.qtech.randomthingz.item.tools.trait;

import com.qtech.randomthingz.effect.common.ModEffects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.Color;

public class SharpTrait extends AbstractPotionTrait {
    public SharpTrait() {

    }

    @Override
    public Color getColor() {
        return Color.fromHex("#df1300");
    }

    @Override
    public EffectInstance getEffectInstance() {
        return new EffectInstance(ModEffects.BLEEDING.get(), 240, 2);
    }
}
