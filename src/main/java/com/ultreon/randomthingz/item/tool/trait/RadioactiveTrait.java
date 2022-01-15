package com.ultreon.randomthingz.item.tool.trait;

import com.ultreon.randomthingz.effect.common.ModEffects;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;

public class RadioactiveTrait extends AbstractPotionTrait {
    @Override
    public TextColor getColor() {
        return TextColor.parseColor("#009F00");
    }

    @Override
    public MobEffectInstance getEffectInstance() {
        return new MobEffectInstance(ModEffects.RADIATION.get(), 1200, 5);
    }
}
