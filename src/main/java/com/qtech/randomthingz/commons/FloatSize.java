package com.qtech.randomthingz.commons;

import com.qtech.randomthingz.commons.interfaces.Formattable;
import lombok.AllArgsConstructor;
import net.minecraft.util.text.TextFormatting;

@AllArgsConstructor
public class FloatSize implements Formattable {
    public float width;
    public float height;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public String toFormattedString() {
        return TextFormatting.GOLD.toString() + this.width + TextFormatting.GRAY + " x " + TextFormatting.GOLD + this.height;
    }
}
