package com.ultreon.randomthingz.world.gen.ores;

import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.ultreon.randomthingz.client.gui.modules.ModuleCompatibility;
import com.ultreon.randomthingz.commons.CoreModule;
import com.ultreon.randomthingz.commons.ModuleSafety;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class OresModule extends CoreModule {
    private final ModOreGen oreGen = ModOreGen.getInstance();

    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
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
