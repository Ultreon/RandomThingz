package com.qtech.forgemod.common;

import com.qtech.forgemod.common.interfaces.Formattable;
import com.qtech.forgemod.util.helpers.MathHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.text.TextFormatting;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Multiplier implements Formattable {
    @Getter private final double value;

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
