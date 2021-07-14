package com.qtech.randomthingz.modules.actionmenu;

import com.qtech.randomthingz.commons.text.Translations;
import com.qtech.randomthingz.network.ModuleModifyRequestPacket;
import com.qtech.randomthingz.network.Network;
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
