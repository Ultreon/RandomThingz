package com.qsoftware.forgemod.common.enums;

import com.qsoftware.forgemod.common.interfaces.Indexable;

public enum MoonPhase implements Indexable {
    FULL(0),
    WANING_GIBBOUS(1),
    THIRD_QUARTER(2),
    WANING_CRESCENT(3),
    NEW(4),
    WAXING_CRESCENT(5),
    FIRST_QUARTER(6),
    WAXING_GIBBOUS(7);

    private final int index;

    MoonPhase(int index) {
        this.index = index;
    }

    public static MoonPhase fromIndex(int index) {
        MoonPhase[] values = MoonPhase.values();

        for (MoonPhase value : values) {
            if (value.index == index) {
                return value;
            }
        }

        return null;
    }

    @Override
    public int getIndex() {
        return index;
    }
}