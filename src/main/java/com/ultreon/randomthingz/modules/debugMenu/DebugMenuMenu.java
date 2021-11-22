package com.ultreon.randomthingz.modules.debugMenu;

import com.ultreon.randomthingz.modules.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.modules.actionmenu.ActionMenuItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

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
                public ITextComponent getText() {
                    return new StringTextComponent(page.name().toLowerCase(Locale.ROOT));
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
