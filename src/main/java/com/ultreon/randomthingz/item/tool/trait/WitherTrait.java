package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class WitherTrait extends AbstractPotionTrait {
    public WitherTrait() {

    }

    @Override
    public TextColor getColor() {
        return TextColor.parseColor("#3F3F3F");
    }

    @Override
    public MobEffectInstance getEffectInstance() {
        return new MobEffectInstance(MobEffects.WITHER, 50, 1);
    }
}
