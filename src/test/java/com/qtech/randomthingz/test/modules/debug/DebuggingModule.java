package com.qtech.randomthingz.test.modules.debug;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.gui.modules.ModuleCompatibility;
import com.qtech.randomthingz.commons.Module;
import com.qtech.randomthingz.commons.ModuleSecurity;
import com.qtech.randomthingz.modules.actionmenu.MainActionMenu;
import com.qtech.randomthingz.modules.actionmenu.MenuHandler;
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
        if (RandomThingz.isClientSide()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @Override
    public void onDisable() {
        if (RandomThingz.isClientSide()) {
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
        if (RandomThingz.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (RandomThingz.isServerSide()) {
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
