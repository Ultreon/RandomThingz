package com.qtech.randomthingz.modules.actionmenu;

import net.minecraft.util.text.ITextComponent;

public class SubmenuItem implements IActionMenuItem {
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
    public ITextComponent getText() {
        return handler.getText();
    }
}
