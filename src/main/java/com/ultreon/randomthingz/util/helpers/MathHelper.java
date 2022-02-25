package com.ultreon.randomthingz.util.helpers;

import java.math.BigDecimal;

@SuppressWarnings("unused")
@lombok.experimental.UtilityClass
public final class MathHelper extends UtilityClass {
    public static int getDecimalPlaces(Float d) {
        String s = d.toString();
        if (s.endsWith(".0")) {
            return 0;
        }
        String[] split = s.split("\\.");
        if (split.length == 1) {
            return 0;
        }

        return split[1].length();
    }

    public static int getDecimalPlaces(Double d) {
        String s = d.toString();
        if (s.endsWith(".0")) {
            return 0;
        }
        String[] split = s.split("\\.");
        if (split.length == 1) {
            return 0;
        }

        return split[1].length();
    }

    public static int getDecimalPlaces(BigDecimal d) {
        String s = d.toString();
        if (s.endsWith(".0")) {
            return 0;
        }
        String[] split = s.split("\\.");
        if (split.length == 1) {
            return 0;
        }

        return split[1].length();
    }
}
