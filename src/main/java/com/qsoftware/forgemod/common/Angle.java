package com.qsoftware.forgemod.common;

import net.minecraft.util.text.TextFormatting;

public class Angle implements IFormattable {
    private final double degrees;

    public Angle(double degrees) {
        this.degrees = degrees;
    }
    
    public String toFormattedString() {
        return TextFormatting.BLUE.toString() + this.degrees + ((char)0xb0);
    }
}
