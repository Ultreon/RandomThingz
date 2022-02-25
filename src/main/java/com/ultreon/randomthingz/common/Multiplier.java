package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.common.interfaces.Formattable;
import com.ultreon.randomthingz.util.helpers.MathHelper;
import lombok.Data;
import net.minecraft.ChatFormatting;

import java.util.Objects;

public record Multiplier(double value) implements Formattable {
    @Override
    public String toFormattedString() {
        if (MathHelper.getDecimalPlaces(value) == 0) {
            return ChatFormatting.BLUE.toString() + Math.round(value) + ChatFormatting.GRAY + "x";
        }

        return ChatFormatting.BLUE.toString() + value + ChatFormatting.GRAY + "x";
    }

    public Percentage percentage() {
        return new Percentage(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Multiplier that = (Multiplier) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
