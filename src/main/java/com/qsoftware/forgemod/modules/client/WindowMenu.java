package com.qsoftware.forgemod.modules.client;

import com.qsoftware.forgemod.Modules;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qsoftware.forgemod.modules.actionmenu.IActionMenuItem;
import com.qsoftware.forgemod.modules.confirmExit.ConfirmExitScreen;
import com.qsoftware.forgemod.modules.debugMenu.DebugMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class WindowMenu extends AbstractActionMenu {
    public WindowMenu() {
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (ModuleManager.getInstance().isEnabled(Modules.CONFIRM_EXIT)) {
                    mc.displayGuiScreen(new ConfirmExitScreen(mc.currentScreen));
                } else {
                    mc.shutdown();
                }
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("action.qforgemod.window.close");
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.getMainWindow().toggleFullscreen();
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("action.qforgemod.window.fullscreen");
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.WINDOW;
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("action.qforgemod.set_debug_page");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.dimension != null && mc.playerController != null;
            }
        });
    }
}
