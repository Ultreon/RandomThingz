package com.qtech.randomthingz.modules.client;

import com.qtech.randomthingz.Modules;
import com.qtech.randomthingz.commons.ModuleManager;
import com.qtech.randomthingz.modules.actionmenu.AbstractActionMenu;
import com.qtech.randomthingz.modules.actionmenu.ActionMenuItem;
import com.qtech.randomthingz.modules.confirmExit.ConfirmExitScreen;
import com.qtech.randomthingz.modules.debugMenu.DebugMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
                    mc.displayGuiScreen(new ConfirmExitScreen(mc.currentScreen));
                } else {
                    mc.shutdown();
                }
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("action.randomthingz.window.close");
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.getMainWindow().toggleFullscreen();
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("action.randomthingz.window.fullscreen");
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.WINDOW;
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("action.randomthingz.set_debug_page");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.dimension != null && mc.playerController != null;
            }
        });
    }

    @Override
    public void server() {

    }
}
