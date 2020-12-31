package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.util.helpers.MathHelper;
import net.minecraft.util.text.TextFormatting;

public class Multiplier implements Formattable {
    private final double multiplier;

    public Multiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public String toFormattedString() {
        if (MathHelper.getDecimalPlaces(multiplier) == 0) {
            return TextFormatting.GOLD.toString() + Math.round(multiplier) + TextFormatting.GRAY + "x";
        }

        return TextFormatting.GOLD.toString() + multiplier + TextFormatting.GRAY + "x";
    }
}
