package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;

public class Container implements Formattable {
    private final net.minecraft.inventory.container.Container wrapper;

    public Container(net.minecraft.inventory.container.Container wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public String toFormattedString() {
        return null;
    }
}
