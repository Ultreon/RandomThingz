package com.ultreon.randomthingz.item.tools.trait;

import net.minecraft.util.text.Color;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class ProjectileResistantTrait extends AbstractTrait {
    public ProjectileResistantTrait() {

    }

    @Override
    public Color getColor() {
        return Color.fromHex("#6B5232");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent e) {
        super.onLivingDamage(e);

        if (e.getSource().isProjectile()) {
            e.setCanceled(true);
        }
    }
}
