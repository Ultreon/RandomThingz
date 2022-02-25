package com.ultreon.randomthingz.client;

import com.ultreon.randomthingz.Modules;
import com.ultreon.randomthingz.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.actionmenu.ActionMenuItem;
import com.ultreon.randomthingz.client.debug.menu.DebugGui;
import com.ultreon.randomthingz.client.debug.menu.DebugPages;
import com.ultreon.randomthingz.client.gui.screen.confirmExit.ConfirmExitScreen;
import com.ultreon.randomthingz.common.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WindowMenu extends AbstractActionMenu {
    public WindowMenu() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void client() {
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (ModuleManager.getInstance().isEnabled(Modules.Client.CONFIRM_EXIT)) {
                    mc.setScreen(new ConfirmExitScreen(mc.screen));
                } else {
                    mc.stop();
                }
            }

            @Override
            public Component getText() {
                return new TranslatableComponent("action.randomthingz.window.close");
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.getWindow().toggleFullScreen();
            }

            @Override
            public Component getText() {
                return new TranslatableComponent("action.randomthingz.window.fullscreen");
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                DebugGui.get().setPage(DebugPages.WINDOW);
            }

            @Override
            public Component getText() {
                return new TranslatableComponent("action.randomthingz.set_debug_page");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.level != null && mc.gameMode != null;
            }
        });
    }

    @Override
    public void server() {

    }
}
