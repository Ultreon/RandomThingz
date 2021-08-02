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
public class BleedingEffect extends Effect {
    public BleedingEffect() {
        super(EffectType.HARMFUL, 0xbfbfbf);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attack(ModDamageSources.BLEEDING, 1.0F);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int j = 35 >> amplifier;
        if (j > 0)
            return duration % j == 0;
        else
            return true;
    }
}
