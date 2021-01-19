package com.qsoftware.forgemod.common;

public abstract class DummyModule extends Module {
    @Override
    public final void onEnable() {
        // Do nothing.
    }

    @Override
    public final void onDisable() {
        // Do nothing.
    }

    @Override
    public boolean canDisable() {
        return true;
    }

    @Override
    public abstract String getName();

    @Override
    public abstract boolean isDefaultEnabled();
}
