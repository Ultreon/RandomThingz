package com.qsoftware.forgemod.common.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Infinity sword damage source.
 *
 * @author Qboi123
 */
public class DamageSourceInfinitySword extends EntityDamageSource {

    public DamageSourceInfinitySword(Entity source) {
        super("infinity", source);
    }

    @Override
    public @NotNull ITextComponent getDeathMessage(LivingEntity entity) {
        ItemStack itemstack = damageSourceEntity instanceof LivingEntity ? ((LivingEntity) damageSourceEntity).getHeldItem(Hand.MAIN_HAND) : null;
        String s = "death.attack.infinity";
        int rando = entity.getEntityDimension().rand.nextInt(5);
        if (rando != 0) {
            s = s + "." + rando;
        }
        return new TranslationTextComponent(s, entity.getDisplayName(), Objects.requireNonNull(itemstack).getDisplayName());
    }

    @Override
    public boolean isUnblockable() {
        return true;
    }

    @Override
    public boolean isDifficultyScaled() {
        return false;
    }
}
