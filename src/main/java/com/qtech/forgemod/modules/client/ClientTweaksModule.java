package com.qtech.forgemod.modules.client;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.client.gui.modules.ModuleCompatibility;
import com.qtech.forgemod.common.Module;
import com.qtech.forgemod.common.ModuleManager;
import com.qtech.forgemod.common.ModuleSecurity;
import com.qtech.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qtech.forgemod.modules.actionmenu.MainActionMenu;
import com.qtech.forgemod.modules.actionmenu.IMenuHandler;
import com.qtech.forgemod.modules.actionmenu.MenuHandler;
import com.qtech.forgemod.modules.client.modules.MobVariantsModule;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import lombok.NonNull;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.text.ITextComponent;
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
        if (QForgeMod.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (QForgeMod.isServerSide()) {
            return ModuleCompatibility.NONE;
        } else {
            return ModuleCompatibility.NONE;
        }
    }
}
