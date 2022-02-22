package com.ultreon.randomthingz.entity.damagesource;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Infinity sword damage source.
 *
 * @author Qboi123
 */
public class InfinitySwordDamageSource extends EntityDamageSource {

    public InfinitySwordDamageSource(Entity source) {
        super("infinity", source);
    }

    @Override
    public @NotNull Component getLocalizedDeathMessage(@NotNull LivingEntity entity) {
        ItemStack itemstack = entity.getItemInHand(InteractionHand.MAIN_HAND);
        String s = "death.attack.infinity";
        int rando = entity.getRandom().nextInt(5);
        if (rando != 0) {
            s = s + "." + rando;
        }
        return new TranslatableComponent(s, entity.getDisplayName(), Objects.requireNonNull(itemstack).getHoverName());
    }

    @Override
    public boolean isBypassArmor() {
        return true;
    }

    @Override
    public boolean scalesWithDifficulty() {
        return false;
    }
}
