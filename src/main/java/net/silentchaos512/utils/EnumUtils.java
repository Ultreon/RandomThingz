package net.silentchaos512.utils;

import javax.annotation.Nullable;
import java.util.function.Function;

public final class EnumUtils {
    private EnumUtils() {throw new IllegalAccessError("Utility class");}

    /**
     * Get an enum constant that produces the given value from {@code getter}. Returns the first
     * result, even if multiple constants would match.
     *
     * @return The first match, if one exists, or defaultValue otherwise.
     */
    public static <E extends Enum<E>> E byIndex(int value, E defaultValue, Function<E, Integer> getter) {
        for (E e : defaultValue.getDeclaringClass().getEnumConstants()) {
            if (getter.apply(e) == value) {
                return e;
            }
        }
        return defaultValue;
    }

    /**
     * Get the enum constant with the given name (ignoring case), or {@code defaultValue} if no
     * match is found.
     *
     * @return The enum constant with the given name, or {@code defaultValue} if invalid.
     */
    public static <E extends Enum<E>> E byName(String name, E defaultValue) {
        for (E e : defaultValue.getDeclaringClass().getEnumConstants()) {
            if (e.name().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return defaultValue;
    }

    /**
     * Get the enum constant with the given ordinal, or {@code defaultValue} if out-of-bounds.
     *
     * @return The enum constant with the given ordinal, or {@code defaultValue} if ordinal is not
     * valid.
     */
    public static <E extends Enum<E>> E byOrdinal(int ordinal, E defaultValue) {
        E[] enumConstants = defaultValue.getDeclaringClass().getEnumConstants();
        if (ordinal >= 0 && ordinal < enumConstants.length) {
            return enumConstants[ordinal];
        }
        return defaultValue;
    }

    /**
     * Check the object is a valid constant of the enum class, ignoring case.
     *
     * @return True if obj is non-null and {@code obj.toString()} matches a constant in the enum
     * class (ignoring case), false otherwise.
     */
    public static <E extends Enum<E>> boolean validate(@Nullable Object obj, Class<E> enumClass) {
        if (obj != null) {
            for (E e : enumClass.getEnumConstants()) {
                if (e.name().equalsIgnoreCase(obj.toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}
