package com.qtech.forgemod.common;

import com.qtech.forgemod.common.interfaces.Formattable;
import lombok.Data;
import lombok.Getter;
import net.minecraft.util.text.TextFormatting;

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
