package com.qtech.forgemod.commons;

import com.qtech.forgemod.commons.interfaces.Formattable;
import lombok.Data;
import lombok.Getter;
import net.minecraft.util.text.TextFormatting;

@Data
public class Angle implements Formattable {
    @Getter private final double degrees;

    public String toFormattedString() {
        return TextFormatting.BLUE.toString() + this.degrees + ((char)0xb0);
    }

    public double getRadians() {
        return Math.toRadians(this.degrees);
    }
}
