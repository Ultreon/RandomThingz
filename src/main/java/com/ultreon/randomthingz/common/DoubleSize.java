package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.common.interfaces.Formattable;
import lombok.AllArgsConstructor;
import net.minecraft.ChatFormatting;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleSize that = (DoubleSize) o;
        return Double.compare(that.width, width) == 0 && Double.compare(that.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
