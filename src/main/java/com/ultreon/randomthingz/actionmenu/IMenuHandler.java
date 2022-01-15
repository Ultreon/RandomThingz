package com.ultreon.randomthingz.actionmenu;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public interface IMenuHandler {
    AbstractActionMenu getMenu();

    default Component getText() {
        return new TextComponent("...");
    }

    boolean isEnabled();
}
