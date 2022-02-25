package com.ultreon.randomthingz.client.debug.menu;

import com.ultreon.randomthingz.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.actionmenu.ActionMenuItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class DebugInfoMenu extends AbstractActionMenu {
    public DebugInfoMenu() {

    }

    @Override
    public void client() {
        for (DebugPage page : DebugGui.get().getPages()) {
            addClient(new ActionMenuItem() {
                @Override
                public void onActivate() {
                    DebugGui.get().setPage(page);
                }

                @Override
                public Component getText() {
                    return new TextComponent(page.registryName().toString());
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
