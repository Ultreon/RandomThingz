package com.qsoftware.forgemod.modules;

import com.qsoftware.forgemod.client.gui.widgets.ModuleCompatibility;
import com.qsoftware.forgemod.common.CoreModule;
import com.qsoftware.forgemod.common.Module;
import org.jetbrains.annotations.NotNull;

public class MainModule extends CoreModule {
    @Override
    public void onEnable() {
        // Todo: add enabling for Main Module.
    }

    @Override
    public @NotNull String getName() {
        return "main";
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }
}
