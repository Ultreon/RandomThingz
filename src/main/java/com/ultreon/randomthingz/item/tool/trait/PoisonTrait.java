package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class PoisonTrait extends AbstractPotionTrait {
    public PoisonTrait() {

    }

    @Override
    public TextColor getColor() {
        return TextColor.parseColor("#007F3F");
    }

    @Override
    public MobEffectInstance getEffectInstance() {
        return new MobEffectInstance(MobEffects.POISON, 50, 1);
    }
}
