package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import net.minecraft.util.text.TextFormatting;

public class Angle implements Formattable {
    private final double degrees;

    public Angle(double degrees) {
        this.degrees = degrees;
    }
    
    public String toFormattedString() {
        return TextFormatting.BLUE.toString() + this.degrees + ((char)0xb0);
    }
}
