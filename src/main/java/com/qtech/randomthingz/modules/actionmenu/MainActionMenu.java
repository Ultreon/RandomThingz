package com.qtech.randomthingz.modules.actionmenu;

import java.util.ArrayList;
import java.util.List;

public final class MainActionMenu extends AbstractActionMenu {
    static final MainActionMenu INSTANCE = new MainActionMenu();
    private final List<SubmenuItem> menuItems = new ArrayList<>();

    private MainActionMenu() {

    }

    @Override
    public void client() {

    }

    @Override
    public void server() {

    }

    public static void registerHandler(IMenuHandler handler) {
        INSTANCE.add(new SubmenuItem(handler));
    }
}
