package com.ultreon.randomthingz.item.tool.trait;

import com.ultreon.randomthingz.init.ModEffects;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;

public class SharpTrait extends AbstractPotionTrait {
    public SharpTrait() {

    }

    @Override
    public TextColor getColor() {
        return TextColor.parseColor("#df1300");
    }

    @Override
    public MobEffectInstance getEffectInstance() {
        return new MobEffectInstance(ModEffects.BLEEDING.get(), 240, 2);
    }
}
