package com.ultreon.randomthingz.actionmenu;

import net.minecraft.network.chat.Component;

public class SubmenuItem extends ActionMenuItem {
    private final IMenuHandler handler;

    public SubmenuItem(IMenuHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onActivate() {

    }

    public IMenuHandler getHandler() {
        return handler;
    }

    @Override
    public boolean isEnabled() {
        return handler.isEnabled();
    }

    @Override
    public Component getText() {
        return handler.getText();
    }
}
