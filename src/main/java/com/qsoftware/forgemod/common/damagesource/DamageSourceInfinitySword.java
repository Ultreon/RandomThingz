package com.qsoftware.forgemod.common.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Objects;

public class DamageSourceInfinitySword extends EntityDamageSource {

    public DamageSourceInfinitySword(Entity source) {
        super("infinity", source);
    }

    @Override
    public ITextComponent getDeathMessage(LivingEntity entity) {
        ItemStack itemstack = damageSourceEntity instanceof LivingEntity ? ((LivingEntity) damageSourceEntity).getHeldItem(Hand.MAIN_HAND) : null;
        String s = "death.attack.infinity";
        int rando = entity.getEntityWorld().rand.nextInt(5);
        if (rando != 0) {
            s = s + "." + rando;
        }
        return new TranslationTextComponent(s, entity.getDisplayName(), Objects.requireNonNull(itemstack).getDisplayName());
    }

    @Override
    public boolean isDifficultyScaled() {
        return false;
    }

}