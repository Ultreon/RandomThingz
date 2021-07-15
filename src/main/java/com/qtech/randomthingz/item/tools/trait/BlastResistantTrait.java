package com.qtech.randomthingz.item.tools.trait;

import com.qtech.randomthingz.item.tools.ToolType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Color;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.Set;

public class BlastResistantTrait extends AbstractTrait {
    public BlastResistantTrait() {

    }

    @Override
    public Color getColor() {
        return Color.fromHex("#BE031D");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent e) {
        super.onLivingDamage(e);

        if (e.getSource().isExplosion()) {
            e.setCanceled(true);
        }
    }
}
