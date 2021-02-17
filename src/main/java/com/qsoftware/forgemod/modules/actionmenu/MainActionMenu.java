package com.qsoftware.forgemod.modules.actionmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public final class MainActionMenu extends AbstractActionMenu {
    static final MainActionMenu INSTANCE = new MainActionMenu();
    private final List<MenuItem> menuItems = new ArrayList<>();

    private MainActionMenu() {

    }

    public static class MenuItem implements IActionMenuItem {
        private final MenuHandler handler;

        public MenuItem(MenuHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onActivate() {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new ActionMenuScreen(mc.currentScreen, this.handler.getMenu()));
        }

        @Override
        public boolean isEnabled() {
            return handler.isEnabled();
        }

        @Override
        public ITextComponent getText() {
            return handler.getText();
        }
    }

    public static void registerHandler(MenuHandler handler) {
        INSTANCE.addItem(new MenuItem(handler));
    }
}
