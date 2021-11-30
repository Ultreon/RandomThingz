package com.ultreon.randomthingz.item.tool.trait;

import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.Color;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SlimeyTrait extends AbstractTrait {
    public SlimeyTrait() {

    }

    @Override
    public boolean onHitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, LivingEntity attacker) {
        if (attacker.dimension.isClientSided) {
            // Don't do anything on client.
            return true;
        }

        victim.getEntityDimension().playSound(victim.getPosX(), victim.getPosY(), victim.getPosZ(), SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        return true;
    }

    @Override
    public Color getColor() {
        return Color.fromHex("#76BE6D");
    }

    @Override
    public float getKnockback(Set<ToolType> qfmToolTypes) {
        return 4.0f;
    }
}
