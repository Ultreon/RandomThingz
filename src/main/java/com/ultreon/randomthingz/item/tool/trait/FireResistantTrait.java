package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.network.chat.TextColor;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class FireResistantTrait extends AbstractTrait {
    public FireResistantTrait() {

    }

    @Override
    public TextColor getColor() {
        return TextColor.parseColor("#E16B16");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent e) {
        super.onLivingDamage(e);

        if (e.getSource().isFire()) {
            e.setCanceled(true);
        }
    }
}
