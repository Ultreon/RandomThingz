package com.ultreon.randomthingz.item.tool.trait;

import com.ultreon.randomthingz.item.tool.ToolType;
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
    public float getKnockback(Set<ToolType> qfmToolTypes) {
        return 2.0f;
    }
}
