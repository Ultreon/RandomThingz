package com.ultreon.randomthingz.client;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.actionmenu.MainActionMenu;
import com.ultreon.randomthingz.actionmenu.MenuHandler;
import com.ultreon.randomthingz.client.gui.modules.ModuleCompatibility;
import com.ultreon.randomthingz.common.Module;
import com.ultreon.randomthingz.common.ModuleManager;
import com.ultreon.randomthingz.common.ModuleSafety;
import lombok.NonNull;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.TextComponent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ClientTweaksModule extends Module {
    public final MobVariantsModule MOB_VARIANTS = submoduleManager.register(new MobVariantsModule());
    private static final WindowMenu windowMenu = new WindowMenu();

    public ClientTweaksModule() {
        super();

        enableSubManager();

//        registerSubmodule();

        MainActionMenu.registerHandler(new MenuHandler(new TextComponent("Window"), windowMenu));
    }

    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
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
