package com.qsoftware.forgemod.modules.client;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.common.ModuleSecurity;
import com.qsoftware.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qsoftware.forgemod.modules.actionmenu.MainActionMenu;
import com.qsoftware.forgemod.modules.actionmenu.IMenuHandler;
import com.qsoftware.forgemod.modules.actionmenu.MenuHandler;
import com.qsoftware.forgemod.modules.client.modules.MobVariantsModule;
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
