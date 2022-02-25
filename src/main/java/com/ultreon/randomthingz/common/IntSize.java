package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.common.interfaces.Formattable;
import lombok.AllArgsConstructor;
import net.minecraft.ChatFormatting;

import java.util.Objects;

import static net.minecraft.ChatFormatting.BLUE;
import static net.minecraft.ChatFormatting.GRAY;

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
        return BLUE.toString() + this.width + GRAY + " x " + BLUE + this.height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntSize size = (IntSize) o;
        return width == size.width && height == size.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
