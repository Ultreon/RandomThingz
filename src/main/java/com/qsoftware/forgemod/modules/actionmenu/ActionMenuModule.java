package com.qsoftware.forgemod.modules.actionmenu;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleSecurity;
import com.qsoftware.forgemod.keybinds.KeyBindingList;
import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ActionMenuModule extends Module {
    private final ClientSide clientSide;
    private final ServerSide serverSide;

    public ActionMenuModule() {
        if (QForgeMod.isClientSide()) {
            this.clientSide = new ClientSide(this);
            this.serverSide = null;
        } else if (QForgeMod.isServerSide()) {
            this.serverSide = new ServerSide(this);
            this.clientSide = null;
        } else {
            this.clientSide = null;
            this.serverSide = null;
        }
    }

    @Override
    public void onEnable() {
        if (QForgeMod.isClientSide()) {
            MinecraftForge.EVENT_BUS.register(this.clientSide);
        } else if (QForgeMod.isServerSide()) {
            MinecraftForge.EVENT_BUS.register(this.serverSide);
        }
    }

    @Override
    public void onDisable() {
        if (QForgeMod.isClientSide()) {
            MinecraftForge.EVENT_BUS.unregister(this.clientSide);
        } else if (QForgeMod.isServerSide()) {
            MinecraftForge.EVENT_BUS.unregister(this.serverSide);
        }
    }

    @Override
    public boolean canDisable() {
        return true;
    }

    @Override
    public String getName() {
        return "action_menu";
    }

    @Override
    public boolean isDefaultEnabled() {
        return true;
    }

    @Override
    public ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }

    @Override
    public ModuleSecurity getSecurity() {
        return ModuleSecurity.SAFE;
    }

    public static class ClientSide extends Module.ClientSide {
        @Getter
        private final ActionMenuModule module;

        public ClientSide(ActionMenuModule module) {
            this.module = module;
        }

        @SubscribeEvent
        public void clientTick(TickEvent.ClientTickEvent event) {
            if (KeyBindingList.ACTION_MENU.isPressed()) {
                Minecraft mc = Minecraft.getInstance();
                mc.displayGuiScreen(new ActionMenuScreen(mc.currentScreen, MainActionMenu.INSTANCE));
            }
        }
    }

    public static class ServerSide extends Module.ServerSide {
        @Getter
        private final ActionMenuModule module;

        public ServerSide(ActionMenuModule module) {
            this.module = module;
        }
    }
}
