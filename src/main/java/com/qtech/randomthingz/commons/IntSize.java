package com.qtech.randomthingz.commons;

import com.qtech.randomthingz.commons.interfaces.Formattable;
import lombok.AllArgsConstructor;
import net.minecraft.util.text.TextFormatting;

@AllArgsConstructor
public class IntSize implements Formattable {
    public int width;
    public int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toFormattedString() {
        return TextFormatting.GOLD.toString() + this.width + TextFormatting.GRAY + " x " + TextFormatting.GOLD + this.height;
    }
}
