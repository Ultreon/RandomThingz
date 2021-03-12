package com.qsoftware.forgemod.modules.items.tools.trait;

import com.qsoftware.forgemod.modules.environment.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import org.jetbrains.annotations.NotNull;

public class RadioactiveTrait extends AbstractTrait {
    public RadioactiveTrait() {

    }

    public EffectInstance getEffectInstance() {
        return new EffectInstance(ModEffects.RADIATION.get(), 1200, 2);
    }

    @Override
    public boolean onHitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, LivingEntity attacker) {
        victim.addPotionEffect(getEffectInstance());
        return super.onHitEntity(stack, victim, attacker);
    }

    @Override
    public void onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).addPotionEffect(getEffectInstance());
        }
    }
}
