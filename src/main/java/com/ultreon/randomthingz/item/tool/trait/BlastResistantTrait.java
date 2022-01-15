package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.network.chat.TextColor;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class BlastResistantTrait extends AbstractTrait {
    public BlastResistantTrait() {

    }

    @Override
    public TextColor getColor() {
        return TextColor.parseColor("#BE031D");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent e) {
        super.onLivingDamage(e);

        if (e.getSource().isExplosion()) {
            e.setCanceled(true);
        }
    }
}
