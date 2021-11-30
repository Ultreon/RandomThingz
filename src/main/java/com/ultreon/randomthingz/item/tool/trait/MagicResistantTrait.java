package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.util.text.Color;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class MagicResistantTrait extends AbstractTrait {
    public MagicResistantTrait() {

    }

    @Override
    public Color getColor() {
        return Color.fromHex("#8603BE");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent e) {
        super.onLivingDamage(e);

        if (e.getSource().isProjectile()) {
            e.setCanceled(true);
        }
    }
}
