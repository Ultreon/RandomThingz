package com.qtech.forgemod.effect;

import com.qtech.forgemod.block.AtomicTNTBlock;
import com.qtech.forgemod.commons.damagesource.ModDamageSources;
import com.qtech.forgemod.item.tools.Tools;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

/**
 * Radiation potion effect, does one heart of damage very slowly. Can kill all living entities.
 *
 * @see Tools#URANIUM
 * @see AtomicTNTBlock
 */
public class RadiationEffect extends Effect {
    public RadiationEffect() {
        super(EffectType.HARMFUL, 0x408040);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attack(ModDamageSources.RADIATION, 2.0F);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int i = 1200 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}
