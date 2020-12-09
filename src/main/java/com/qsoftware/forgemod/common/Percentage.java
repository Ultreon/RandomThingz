package com.qsoftware.forgemod.common;

import net.minecraft.util.text.TextFormatting;

public class Percentage implements IFormattable {
    private final double percentage;

    public Percentage(double value) {
        this.percentage = value * 100;
    }

    public Percentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toFormattedString() {
        return TextFormatting.BLUE.toString() + Math.round(percentage) + "%";
    }
}
