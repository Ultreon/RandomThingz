package com.qtech.randomthingz.commons;

import com.qtech.randomthingz.commons.interfaces.Formattable;
import lombok.Data;
import lombok.Getter;
import net.minecraft.util.text.TextFormatting;

/**
 * Percentage class, used for get percentage value or normalized value.<br>
 * Use {@link #getPercentage() getPercentage} or {@link #setPercentage(double) setPercentage} for getting / setting the percentage value.<br>
 * Use {@link #getValue() getValue} or {@link #setValue(double) setValue} for getting / settings the normalized value.<br>
 * <br>
 * @author Qboi123
 */
@Data
public class Percentage implements Formattable {
    @Getter private double percentage;

    public Percentage(double value) {
        this.percentage = value * 100;
    }

    public Percentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toFormattedString() {
        return TextFormatting.BLUE.toString() + Math.round(percentage) + "%";
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
}
