package com.qsoftware.forgemod.common;

public abstract class CoreModule extends Module {
    @Override
    public final void onDisable() {

    }

    @Override
    public final boolean isDefaultEnabled() {
        return true;
    }

    @Override
    public final boolean canDisable() {
        return false;
    }
}
