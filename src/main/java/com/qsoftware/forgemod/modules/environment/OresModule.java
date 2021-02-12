package com.qsoftware.forgemod.modules.environment;

import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.CoreModule;
import com.qsoftware.forgemod.common.ModuleSecurity;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class OresModule extends CoreModule {
    private final ModOreGen oreGen = ModOreGen.getInstance();

    @Override
    public ModuleSecurity getSecurity() {
        return ModuleSecurity.SAFE;
    }

    @Override
    public void onEnable() {
        this.modEventBus.register(new OresInitializer(this.oreGen));
        this.forgeEventBus.register(this.oreGen);
    }

    @Override
    public String getName() {
        return "ores";
    }

    @Override
    public ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }
}
