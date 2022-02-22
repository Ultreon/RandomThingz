package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.common.interfaces.Formattable;
import com.ultreon.randomthingz.util.helpers.MathHelper;
import lombok.Data;
import net.minecraft.ChatFormatting;

@Data
public class Multiplier implements Formattable {
    private final double value;

    @Override
    public String toFormattedString() {
        if (MathHelper.getDecimalPlaces(value) == 0) {
            return ChatFormatting.GOLD.toString() + Math.round(value) + ChatFormatting.GRAY + "x";
        }

        return ChatFormatting.GOLD.toString() + value + ChatFormatting.GRAY + "x";
    }

    public Percentage toPercentage() {
        return new Percentage(value);
    }
}
