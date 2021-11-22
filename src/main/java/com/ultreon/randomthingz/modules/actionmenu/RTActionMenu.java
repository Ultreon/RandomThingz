package com.ultreon.randomthingz.modules.actionmenu;

import com.ultreon.randomthingz.commons.text.Translations;
import com.ultreon.randomthingz.network.ModuleModifyRequestPacket;
import com.ultreon.randomthingz.network.Network;
import net.minecraft.util.text.ITextComponent;

public class RTActionMenu extends AbstractActionMenu {
    @Override
    public void client() {
        add(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Network.sendToServer(new ModuleModifyRequestPacket());
            }

            @Override
            public ITextComponent getText() {
                return Translations.getScreen("settings", "modules");
            }
        });
    }

    @Override
    public void server() {

    }
}
