package com.ultreon.randomthingz.effect;

import com.ultreon.randomthingz.block.AtomicTNTBlock;
import com.ultreon.randomthingz.entity.damagesource.ModDamageSources;
import com.ultreon.randomthingz.item.tools.Toolset;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

/**
 * Radiation potion effect, does one heart of damage very slowly. Can kill all living entities.
 *
 * @see Toolset#URANIUM
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
        int i;

        if (duration == 0) i = 0;
        else i = (1800 * amplifier) / duration;

        if (i > 0) return duration % i == 0;
        else return true;
    }
}