package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.common.interfaces.Formattable;
import lombok.AllArgsConstructor;
import net.minecraft.ChatFormatting;

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
        return ChatFormatting.GOLD.toString() + this.width + ChatFormatting.GRAY + " x " + ChatFormatting.GOLD + this.height;
    }
}
