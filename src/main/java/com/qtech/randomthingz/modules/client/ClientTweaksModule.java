package com.qtech.randomthingz.modules.client;

import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.gui.modules.ModuleCompatibility;
import com.qtech.randomthingz.commons.Module;
import com.qtech.randomthingz.commons.ModuleManager;
import com.qtech.randomthingz.commons.ModuleSecurity;
import com.qtech.randomthingz.modules.actionmenu.MainActionMenu;
import com.qtech.randomthingz.modules.actionmenu.MenuHandler;
import com.qtech.randomthingz.modules.client.modules.MobVariantsModule;
import lombok.NonNull;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ClientTweaksModule extends Module {
    public final MobVariantsModule MOB_VARIANTS = submoduleManager.register(new MobVariantsModule());
    private static final MinecraftMenu minecraftMenu = new MinecraftMenu();
    private static final WindowMenu windowMenu = new WindowMenu();

    public ClientTweaksModule() {
        super();

        enableSubManager();

//        registerSubmodule();

        MainActionMenu.registerHandler(new MenuHandler(new StringTextComponent("Minecraft"), minecraftMenu));
        MainActionMenu.registerHandler(new MenuHandler(new StringTextComponent("Window"), windowMenu));
    }

    @Override
    public ModuleSecurity getSecurity() {
        return ModuleSecurity.SAFE;
    }

    @Override
    public void onEnable() {
        @NonNull ModuleManager moduleManager = Objects.requireNonNull(getSubmoduleManager());
        for (Module module : moduleManager.getModules()) {
            if (moduleManager.isEnabled(module)) {
                module.onEnable();
            }
        }

    }

    @Override
    public void onDisable() {
        @NonNull ModuleManager moduleManager = Objects.requireNonNull(getSubmoduleManager());
        for (Module module : moduleManager.getModules()) {
            if (moduleManager.isEnabled(module)) {
                module.onDisable();
            }
        }
    }

    @Override
    public boolean canDisable() {
        return true;
    }

    @Override
    public String getName() {
        return "client";
    }

    @Override
    public boolean isDefaultEnabled() {
        return false;
    }

    @Override
    public ModuleCompatibility getCompatibility() {
        if (RandomThingz.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (RandomThingz.isServerSide()) {
            return ModuleCompatibility.NONE;
        } else {
            return ModuleCompatibility.NONE;
        }
    }
}
