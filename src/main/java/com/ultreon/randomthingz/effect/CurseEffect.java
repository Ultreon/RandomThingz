package com.ultreon.randomthingz.effect;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.UUID;

/**
 * @author Qboi123
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CurseEffect extends MobEffect {
    private static final DamageSource DAMAGE_SOURCE = new DamageSource("curse");

    public CurseEffect() {
        super(MobEffectCategory.HARMFUL, 0xff00ff);
        this.addAttributeModifier(Attributes.LUCK, UUID.nameUUIDFromBytes("CURSED!!!".getBytes()).toString()/*""CC5AF142-2BD2-4215-B636-2605AED11727"*/, -4.0D, AttributeModifier.Operation.ADDITION);
    }

    /**
     * This effect is not an instant effect.
     *
     * @return always {@code false}.
     */
    @Override
    public final boolean isInstantenous() {
        return false;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = 20 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Random rng = entity.getRandom();
        switch (rng.nextInt(13)) {
            case 0: {
                entity.hurt(DAMAGE_SOURCE, 1.0F);
                break;
            }
            case 1: {
                entity.setDeltaMovement(((rng.nextDouble() - 0.5d) * 2d) * 10d, ((rng.nextDouble() - 0.5d) * 2d) * 10d, ((rng.nextDouble() - 0.5d) * 2d) * 10d);
                break;
            }
            case 2: {
                entity.setJumping(rng.nextBoolean());
                break;
            }
            case 3: {
                entity.setShiftKeyDown(rng.nextBoolean());
                break;
            }
            case 4: {
                entity.lerpMotion(((rng.nextDouble() - 0.5d) * 2d) * 10d, ((rng.nextDouble() - 0.5d) * 2d) * 10d, ((rng.nextDouble() - 0.5d) * 2d) * 10d);
                break;
            }
            case 5: {
                entity.teleportTo(entity.getX(), entity.getY() + rng.nextInt(20), entity.getZ());
                break;
            }
            case 6: {
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 12000, 5));
                break;
            }
            case 7: {
                entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 12000, 5));
                break;
            }
            case 8: {
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 12000, 5));
                break;
            }
            case 9: {
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 12000, 5));
                break;
            }
            case 10: {
                entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 12000, 5));
                break;
            }
            case 11: {
                entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 12000, 5));
                break;
            }
            case 12: {
                entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 12000, 3));
                break;
            }
            case 13: {
                entity.setSecondsOnFire(2);
                break;
            }
            default: {
                break;
            }
        }
    }
}
