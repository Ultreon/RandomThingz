package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.util.helpers.MathHelper;
import com.qsoftware.modlib.common.interfaces.Formattable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.text.TextFormatting;

@Deprecated
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
