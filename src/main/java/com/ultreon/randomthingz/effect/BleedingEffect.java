package com.ultreon.randomthingz.effect;

import com.ultreon.randomthingz.block.AtomicTNTBlock;
import com.ultreon.randomthingz.entity.damagesource.ModDamageSources;
import com.ultreon.randomthingz.item.tool.Toolset;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * Radiation potion effect, does one heart of damage very slowly. Can kill all living entities.
 *
 * @see Toolset#URANIUM
 * @see AtomicTNTBlock
 */
public class BleedingEffect extends MobEffect {
    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 0xbfbfbf);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.hurt(ModDamageSources.BLEEDING, 1.0F);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = 35 >> amplifier;
        if (j > 0)
            return duration % j == 0;
        else
            return true;
    }
}
