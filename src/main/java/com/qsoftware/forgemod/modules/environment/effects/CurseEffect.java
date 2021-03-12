package com.qsoftware.forgemod.modules.environment.effects;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.UUID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CurseEffect extends Effect {
    private static final DamageSource DAMAGE_SOURCE = new DamageSource("curse");

    public CurseEffect() {
        super(EffectType.HARMFUL, 0xff00ff);
        this.addAttributesModifier(Attributes.LUCK, UUID.nameUUIDFromBytes("CURSED!!!".getBytes()).toString()/*""CC5AF142-2BD2-4215-B636-2605AED11727"*/, -4.0D, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int j = 20 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        Random rng = entity.getRNG();
        switch (rng.nextInt(13)) {
            case 0: {
                entity.attackEntityFrom(DAMAGE_SOURCE, 1.0F);
                break;
            }
            case 1: {
                entity.setMotion(((rng.nextDouble() - 0.5d) * 2d) * 10d, ((rng.nextDouble() - 0.5d) * 2d) * 10d, ((rng.nextDouble() - 0.5d) * 2d) * 10d);
                break;
            }
            case 2: {
                entity.setJumping(rng.nextBoolean());
                break;
            }
            case 3: {
                entity.setSneaking(rng.nextBoolean());
                break;
            }
            case 4: {
                entity.setVelocity(((rng.nextDouble() - 0.5d) * 2d) * 10d, ((rng.nextDouble() - 0.5d) * 2d) * 10d, ((rng.nextDouble() - 0.5d) * 2d) * 10d);
                break;
            }
            case 5: {
                entity.setPositionAndUpdate(entity.getPosX(), entity.getPosY() + rng.nextInt(20), entity.getPosZ());
                break;
            }
            case 6: {
                entity.addPotionEffect(new EffectInstance(Effects.POISON, 12000, 5));
                break;
            }
            case 7: {
                entity.addPotionEffect(new EffectInstance(Effects.WITHER, 12000, 5));
                break;
            }
            case 8: {
                entity.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 12000, 5));
                break;
            }
            case 9: {
                entity.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 12000, 5));
                break;
            }
            case 10: {
                entity.addPotionEffect(new EffectInstance(Effects.HUNGER, 12000, 5));
                break;
            }
            case 11: {
                entity.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 12000, 5));
                break;
            }
            case 12: {
                entity.addPotionEffect(new EffectInstance(Effects.LEVITATION, 12000, 3));
                break;
            }
            case 13: {
                entity.setFire(2);
                break;
            }
            default: {
                break;
            }
        }
    }
}
