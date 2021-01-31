package com.qsoftware.forgemod.modules.client;

import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.modules.client.modules.MobVariantsModule;

public class ClientModule extends Module {
    public final MobVariantsModule MOB_VARIANTS = submoduleManager.register(new MobVariantsModule());

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean canDisable() {
        return false;
    }

    @Override
    public String getName() {
        return "client";
    }

    @Override
    public boolean isDefaultEnabled() {
        return false;
    }

    public ClientModule() {
        super();

        enableSubmodules();
    }

    @Override
    public ModuleCompatibility getCompatibility() {
        return null;
    }

}
