package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.common.interfaces.Formattable;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

import static net.minecraft.ChatFormatting.BLUE;
import static net.minecraft.ChatFormatting.GRAY;

/**
 * Percentage class, used for get percentage value or normalized value.<br>
 * Use {@link #getPercentage() getPercentage} or {@link #setPercentage(double) setPercentage} for getting / setting the percentage value.<br>
 * Use {@link #getValue() getValue} or {@link #setValue(double) setValue} for getting / settings the normalized value.<br>
 * <br>
 *
 * @author Qboi123
 */
@Data
public class Percentage implements Formattable {
    @Getter
    private double percentage;

    public Percentage(double value) {
        this.percentage = value * 100;
    }

    public Percentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toFormattedString() {
        return BLUE.toString() + Math.round(percentage) + GRAY + "%";
    }

    public double getValue() {
        return percentage / 100;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setValue(double value) {
        this.percentage = value * 100;
    }

    public Multiplier toMultiplier() {
        return new Multiplier(this.percentage / 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Percentage that = (Percentage) o;
        return Double.compare(that.percentage, percentage) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentage);
    }
}
