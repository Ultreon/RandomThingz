package com.ultreon.randomthingz.client.debug.menu;

import com.ultreon.randomthingz.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.actionmenu.ActionMenuItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.Locale;

public class DebugMenuMenu extends AbstractActionMenu {
    public DebugMenuMenu() {

    }

    @Override
    public void client() {

        for (DebugMenu.PAGE page : DebugMenu.PAGE.values()) {
            addClient(new ActionMenuItem() {
                @Override
                public void onActivate() {
                    DebugMenu.DEBUG_PAGE = page;
                }

                @Override
                public Component getText() {
                    return new TextComponent(page.name().toLowerCase(Locale.ROOT));
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            });
        }
    }

    @Override
    public void server() {

    }
}
