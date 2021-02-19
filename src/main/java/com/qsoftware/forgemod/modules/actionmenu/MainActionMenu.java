package com.qsoftware.forgemod.modules.actionmenu;

import java.util.ArrayList;
import java.util.List;

public final class MainActionMenu extends AbstractActionMenu {
    static final MainActionMenu INSTANCE = new MainActionMenu();
    private final List<SubmenuItem> menuItems = new ArrayList<>();

    private MainActionMenu() {

    }

    public static void registerHandler(IMenuHandler handler) {
        INSTANCE.addItem(new SubmenuItem(handler));
    }
}
