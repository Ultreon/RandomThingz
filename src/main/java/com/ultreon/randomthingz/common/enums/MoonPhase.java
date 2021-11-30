package com.ultreon.randomthingz.common.enums;

import com.ultreon.randomthingz.common.interfaces.Indexable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MoonPhase implements Indexable {
    FULL(0),
    WANING_GIBBOUS(1),
    THIRD_QUARTER(2),
    WANING_CRESCENT(3),
    NEW(4),
    WAXING_CRESCENT(5),
    FIRST_QUARTER(6),
    WAXING_GIBBOUS(7);

    @Getter
    private final int index;

    public static MoonPhase fromIndex(int index) {
        MoonPhase[] values = MoonPhase.values();

        for (MoonPhase value : values) {
            if (value.index == index) {
                return value;
            }
        }

        return null;
    }
}
