package com.qtech.randomthingz.item.tools.trait;

import net.minecraft.util.text.Color;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class FireResistantTrait extends AbstractTrait {
    public FireResistantTrait() {

    }

    @Override
    public Color getColor() {
        return Color.fromHex("#E16B16");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent e) {
        super.onLivingDamage(e);

        if (e.getSource().isFireDamage()) {
            e.setCanceled(true);
        }
    }
}
