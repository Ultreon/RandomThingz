package com.ultreon.randomthingz.item.tool.trait;

import com.ultreon.randomthingz.item.ItemType;
import net.minecraft.network.chat.TextColor;

import java.util.Set;

public class KnockbackTrait extends AbstractTrait {
    public KnockbackTrait() {

    }

    @Override
    public TextColor getColor() {
        return TextColor.parseColor("#B0D4ED");
    }

    @Override
    public float getKnockback(Set<ItemType> qfmToolTypes) {
        return 2f;
    }
}
