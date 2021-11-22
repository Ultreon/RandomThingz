package com.ultreon.randomthingz.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ExceptionUtil {
    public static UnsupportedOperationException utilityConstructor() {
        return new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
