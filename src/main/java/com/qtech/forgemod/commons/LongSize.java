package com.qtech.forgemod.commons;

import com.qtech.forgemod.commons.interfaces.Formattable;
import lombok.AllArgsConstructor;
import net.minecraft.util.text.TextFormatting;

@AllArgsConstructor
public class LongSize extends AbstractSize implements Formattable {
    public double width;
    public double height;

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toFormattedString() {
        return TextFormatting.GOLD.toString() + this.width + TextFormatting.GRAY + " x " + TextFormatting.GOLD + this.height;
    }
}
