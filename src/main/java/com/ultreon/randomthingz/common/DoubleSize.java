package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.common.interfaces.Formattable;
import lombok.AllArgsConstructor;
import net.minecraft.ChatFormatting;

@AllArgsConstructor
public class DoubleSize implements Formattable {
    public double width;
    public double height;

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toFormattedString() {
        return ChatFormatting.GOLD.toString() + this.width + ChatFormatting.GRAY + " x " + ChatFormatting.GOLD + this.height;
    }
}
