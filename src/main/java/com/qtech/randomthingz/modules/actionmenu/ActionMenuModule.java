package com.qtech.randomthingz.modules.actionmenu;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.gui.modules.ModuleCompatibility;
import com.qtech.randomthingz.client.keybinds.KeyBindingList;
import com.qtech.randomthingz.commons.Module;
import com.qtech.randomthingz.commons.ModuleSafety;
import com.qtech.randomthingz.modules.client.MinecraftMenu;
import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ActionMenuModule extends Module {
    private final ClientSide clientSide;
    private final ServerSide serverSide;
    private static final MinecraftMenu minecraftMenu = new MinecraftMenu();

    public ActionMenuModule() {
        MainActionMenu.registerHandler(new MenuHandler(new StringTextComponent("Minecraft"), minecraftMenu));

        if (RandomThingz.isClientSide()) {
            this.clientSide = new ClientSide(this);
            this.serverSide = null;
        } else if (RandomThingz.isServerSide()) {
            this.serverSide = new ServerSide(this);
            this.clientSide = null;
        } else {
            this.clientSide = null;
            this.serverSide = null;
        }
    }

    @Override
    public void onEnable() {
        if (RandomThingz.isClientSide()) {
            MinecraftForge.EVENT_BUS.register(this.clientSide);
        } else if (RandomThingz.isServerSide()) {
            MinecraftForge.EVENT_BUS.register(this.serverSide);
        }
    }

    @Override
    public void onDisable() {
        if (RandomThingz.isClientSide()) {
            MinecraftForge.EVENT_BUS.unregister(this.clientSide);
        } else if (RandomThingz.isServerSide()) {
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
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
    }

    public static class ClientSide extends Module.ClientSide {
        @Getter
        private final ActionMenuModule module;

        public ClientSide(ActionMenuModule module) {
            this.module = module;
        }

        @SubscribeEvent
        public void clientTick(TickEvent.ClientTickEvent event) {
            Minecraft mc = Minecraft.getInstance();
            if (KeyBindingList.ACTION_MENU.isPressed() && !(mc.currentScreen instanceof ActionMenuScreen)) {
                mc.displayGuiScreen(new ActionMenuScreen(mc.currentScreen, MainActionMenu.INSTANCE, 0));
            }
        }
    }

    public static class ServerSide extends Module.ServerSide {
        @Getter
        private final ActionMenuModule module;

        public ServerSide(ActionMenuModule module) {
//            super(module, server);
            this.module = module;
        }
    }
}
