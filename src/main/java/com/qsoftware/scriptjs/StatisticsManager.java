package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;

public class StatisticsManager implements Formattable {
    private final net.minecraft.stats.StatisticsManager wrapper;

    public StatisticsManager(net.minecraft.stats.StatisticsManager wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public String toFormattedString() {
        return null;
    }
}
