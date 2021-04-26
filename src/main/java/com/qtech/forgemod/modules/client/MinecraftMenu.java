package com.qtech.forgemod.modules.client;

import com.qtech.forgemod.Modules;
import com.qtech.forgemod.common.ModuleManager;
import com.qtech.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qtech.forgemod.modules.actionmenu.IActionMenuItem;
import com.qtech.forgemod.modules.actionmenu.MenuHandler;
import com.qtech.forgemod.modules.actionmenu.SubmenuItem;
import com.qtech.forgemod.modules.confirmExit.ConfirmExitScreen;
import com.qtech.forgemod.modules.debugMenu.DebugMenu;
import com.qtech.forgemod.util.WorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screen.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MinecraftMenu extends AbstractActionMenu {
    private static final OptionsMenu optionsMenu = new OptionsMenu();

    public MinecraftMenu() {
        addItem(new SubmenuItem(new MenuHandler(new TranslationTextComponent("menu.options"), optionsMenu)) {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.displayGuiScreen(new OptionsScreen(mc.currentScreen, mc.gameSettings));
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                WorldUtils.saveWorldThenOpenTitle();
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("menu.returnToMenu");
            }
        });
//        addItem(new IActionMenuItem() {
//            @SuppressWarnings("ConstantConditions")
//            @Override
//            public void onActivate() {
//                Minecraft mc = Minecraft.getInstance();
//                mc.displayGuiScreen(new OptionsScreen(mc.currentScreen, mc.gameSettings));
//            }
//
//            @Override
//            public ITextComponent getText() {
//                return new StringTextComponent("Open Options");
//            }
//        });
        addItem(new IActionMenuItem() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.displayGuiScreen(new ShareToLanScreen(mc.currentScreen));
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("menu.shareToLan");
            }
        });
        addItem(new IActionMenuItem() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.displayGuiScreen(new StatsScreen(mc.currentScreen, mc.player.getStats()));
                }
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("gui.stats");
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.displayGuiScreen(new AdvancementsScreen(mc.player.connection.getAdvancementManager()));
                }
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("gui.advancements");
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.MINECRAFT;
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