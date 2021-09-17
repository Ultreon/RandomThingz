package com.ultreon.randomthingz.item.tools.trait;

import com.ultreon.randomthingz.item.tools.ToolType;
import net.minecraft.util.text.Color;

import java.util.Set;

public class KnockbackTrait extends AbstractTrait {
    public KnockbackTrait() {

    }

    @Override
    public Color getColor() {
        return Color.fromHex("#B0D4ED");
    }

    @Override
    public float getKnockback(Set<ToolType> qfmToolTypes) {
        return 2.0f;
    }
}