package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.network.chat.TextColor;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class ProjectileResistantTrait extends AbstractTrait {
    public ProjectileResistantTrait() {

    }

    @Override
    public TextColor getColor() {
        return TextColor.parseColor("#6B5232");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent e) {
        super.onLivingDamage(e);

        if (e.getSource().isProjectile()) {
            e.setCanceled(true);
        }
    }
}
