package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.common.interfaces.Formattable;
import lombok.Getter;

import java.util.Objects;

import static net.minecraft.ChatFormatting.BLUE;
import static net.minecraft.ChatFormatting.GRAY;

public record Angle(@Getter double degrees) implements Formattable {
    public String toFormattedString() {
        return BLUE.toString() + this.degrees + GRAY + ((char) 0xb0);
    }

    public double radians() {
        return Math.toRadians(this.degrees);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Angle angle = (Angle) o;
        return Double.compare(angle.degrees, degrees) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(degrees);
    }
}
