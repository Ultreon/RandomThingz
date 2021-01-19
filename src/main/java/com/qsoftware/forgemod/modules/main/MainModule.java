package com.qsoftware.forgemod.modules.main;

import com.qsoftware.forgemod.common.interfaces.Module;

public class MainModule extends Module {
    @Override
    public void onEnable() {
        // Todo: add enabling for Main Module.
    }

    @Override
    public void onDisable() {
        // Do nothing, it's not allowed to disable this.
    }

    @Override
    public boolean canDisable() {
        return false;
    }

    @Override
    public String getName() {
        return "main";
    }

    @Override
    public boolean isCompatible() {
        return true;
    }

    @Override
    public boolean isDefaultEnabled() {
        return true;
    }
}
