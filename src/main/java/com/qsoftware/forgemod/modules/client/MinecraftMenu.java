package com.qsoftware.forgemod.modules.client;

import com.qsoftware.forgemod.Modules;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qsoftware.forgemod.modules.actionmenu.IActionMenuItem;
import com.qsoftware.forgemod.modules.confirmExit.ConfirmExitScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MinecraftMenu extends AbstractActionMenu {
    public MinecraftMenu() {
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
                return new TranslationTextComponent("menu.quit");
            }
        });
        addItem(new IActionMenuItem() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.displayGuiScreen(new OptionsScreen(mc.currentScreen, mc.gameSettings));
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("menu.options");
            }
        });
    }
}
