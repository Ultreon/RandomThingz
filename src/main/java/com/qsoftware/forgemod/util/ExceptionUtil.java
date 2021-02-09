package com.qsoftware.forgemod.util;

import lombok.experimental.UtilityClass;

@Deprecated
@UtilityClass
public final class ExceptionUtil {
    public static IllegalAccessError utilityConstructor() {
        return new IllegalAccessError("Tried to initialize constructor of utility class.");
    }
}
