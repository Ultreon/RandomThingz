package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.network.chat.TextColor;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class MagicResistantTrait extends AbstractTrait {
    public MagicResistantTrait() {

    }

    @Override
    public TextColor getColor() {
        return TextColor.parseColor("#8603BE");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent e) {
        super.onLivingDamage(e);

        if (e.getSource().isProjectile()) {
            e.setCanceled(true);
        }
    }
}
