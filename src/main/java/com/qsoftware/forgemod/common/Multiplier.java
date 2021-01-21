package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.util.helpers.MathHelper;
import net.minecraft.util.text.TextFormatting;

public class Multiplier implements Formattable {
    private final double value;

    public Multiplier(double value) {
        this.value = value;
    }

    @Override
    public String toFormattedString() {
        if (MathHelper.getDecimalPlaces(value) == 0) {
            return TextFormatting.GOLD.toString() + Math.round(value) + TextFormatting.GRAY + "x";
        }

        return TextFormatting.GOLD.toString() + value + TextFormatting.GRAY + "x";
    }

    public double getValue() {
        return value;
    }

    public Percentage toPercentage() {
        return new Percentage(value);
    }
}
