package com.qtech.forgemod.modules;

import com.qtech.forgemod.client.gui.modules.ModuleCompatibility;
import com.qtech.forgemod.common.CoreModule;
import com.qtech.forgemod.common.ModuleSecurity;
import mcp.MethodsReturnNonnullByDefault;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class MainModule extends CoreModule {
    @Override
    public ModuleSecurity getSecurity() {
        return ModuleSecurity.SAFE;
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
