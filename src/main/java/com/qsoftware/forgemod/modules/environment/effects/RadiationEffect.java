package com.qsoftware.forgemod.modules.environment.effects;

import com.qsoftware.forgemod.common.damagesource.ModDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class RadiationEffect extends Effect {
    public RadiationEffect() {
        super(EffectType.HARMFUL, 0x003f00);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attackEntityFrom(ModDamageSources.RADIATION, 1.0F);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int i = 64 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}
