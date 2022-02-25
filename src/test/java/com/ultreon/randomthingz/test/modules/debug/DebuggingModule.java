package com.ultreon.randomthingz.test.modules.debug;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.actionmenu.MainActionMenu;
import com.ultreon.randomthingz.actionmenu.MenuHandler;
import com.ultreon.randomthingz.client.gui.modules.ModuleCompatibility;
import com.ultreon.randomthingz.common.Module;
import com.ultreon.randomthingz.common.ModuleSafety;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class DebuggingModule extends Module {
    private static final DebuggingMenu debuggingMenu = new DebuggingMenu();

    public DebuggingModule() {

        MainActionMenu.registerHandler(new MenuHandler(new TextComponent("Debugging"), debuggingMenu));
    }

    @Override
    public @NotNull ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
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
        return false;
    }

    @Override
    public @NotNull String getName() {
        return "debugging";
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
