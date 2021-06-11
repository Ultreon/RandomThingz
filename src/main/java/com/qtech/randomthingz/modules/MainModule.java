package com.qtech.randomthingz.modules;

import com.qtech.randomthingz.client.gui.modules.ModuleCompatibility;
import com.qtech.randomthingz.commons.CoreModule;
import com.qtech.randomthingz.commons.ModuleSafety;
import mcp.MethodsReturnNonnullByDefault;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class MainModule extends CoreModule {
    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
    }

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
