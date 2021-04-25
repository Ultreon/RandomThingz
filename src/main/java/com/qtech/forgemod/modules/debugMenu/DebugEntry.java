package com.qtech.forgemod.modules.debugMenu;

import java.util.function.Supplier;

public class DebugEntry {
    private final String key;
    private final Supplier<Object> valueSupplier;

    public DebugEntry(String key, Object value) {
        this(key, () -> value);
    }

    public DebugEntry(String key, Supplier<Object> valueSupplier) {
        this.key = key;
        this.valueSupplier = valueSupplier;
    }

    public Supplier<Object> getValueSupplier() {
        return valueSupplier;
    }

    public String getKey() {
        return key;
    }
}
