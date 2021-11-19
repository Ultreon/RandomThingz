package com.ultreon.randomthingz.commons;

import com.ultreon.randomthingz.commons.interfaces.Formattable;
import com.ultreon.randomthingz.util.helpers.MathHelper;
import lombok.Data;
import net.minecraft.util.text.TextFormatting;

@Data
public class Multiplier implements Formattable {
    private final double value;

    @Override
    public String toFormattedString() {
        if (MathHelper.getDecimalPlaces(value) == 0) {
            return TextFormatting.GOLD.toString() + Math.round(value) + TextFormatting.GRAY + "x";
        }

        return TextFormatting.GOLD.toString() + value + TextFormatting.GRAY + "x";
    }

    public Percentage toPercentage() {
        return new Percentage(value);
    }
}
