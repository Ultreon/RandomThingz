package com.qsoftware.forgemod.common;

import net.minecraft.util.text.TextFormatting;

public class Size implements IFormattable {
    private final int width;
    private final int height;

    public Size(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toFormattedString() {
        return TextFormatting.GOLD.toString() + this.width + TextFormatting.GRAY + " x " + TextFormatting.GOLD + this.height;
    }
}
