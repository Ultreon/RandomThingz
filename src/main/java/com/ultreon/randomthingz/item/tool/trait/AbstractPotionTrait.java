package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPotionTrait extends AbstractTrait {
    @Override
    public boolean onHitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, LivingEntity attacker) {
        victim.addEffect(getEffectInstance());
        return super.onHitEntity(stack, victim, attacker);
    }

    @Override
    public void onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).addEffect(getEffectInstance());
        }
    }

    public abstract MobEffectInstance getEffectInstance();
}
