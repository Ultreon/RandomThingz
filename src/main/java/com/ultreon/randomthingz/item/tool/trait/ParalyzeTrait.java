package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class ParalyzeTrait extends AbstractPotionTrait {
    public ParalyzeTrait() {

    }

    @Override
    public TextColor getColor() {
        return TextColor.parseColor("#fff000");
    }

    @Override
    public MobEffectInstance getEffectInstance() {
        return new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 29);
    }
}
