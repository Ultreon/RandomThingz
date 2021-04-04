package com.qsoftware.forgemod.modules.debug;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleSecurity;
import com.qsoftware.forgemod.modules.actionmenu.MainActionMenu;
import com.qsoftware.forgemod.modules.actionmenu.MenuHandler;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class DebuggingModule extends Module {
    private static final DebuggingMenu debuggingMenu = new DebuggingMenu();

    public DebuggingModule() {

        MainActionMenu.registerHandler(new MenuHandler(new StringTextComponent("Debugging"), debuggingMenu));
    }

    @Override
    public ModuleSecurity getSecurity() {
        return ModuleSecurity.SAFE;
    }

    @Override
    public void onEnable() {
        if (QForgeMod.isClientSide()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @Override
    public void onDisable() {
        if (QForgeMod.isClientSide()) {
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }

    @Override
    public boolean canDisable() {
        return true;
    }

    @Override
    public @NotNull String getName() {
        return "debug_menu";
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        if (QForgeMod.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (QForgeMod.isServerSide()) {
            return ModuleCompatibility.PARTIAL;
        } else {
            return ModuleCompatibility.NONE;
        }
    }

    @Override
    public boolean isDefaultEnabled() {
        return true;
    }
}
