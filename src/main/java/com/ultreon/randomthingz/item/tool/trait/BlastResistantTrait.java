package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.util.text.Color;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

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
