package com.ultreon.randomthingz.test.modules.debug;

import com.ultreon.randomthingz.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.actionmenu.ActionMenuItem;
import com.ultreon.randomthingz.network.Network;
import com.ultreon.randomthingz.test.network.OreProfilePacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DebuggingMenu extends AbstractActionMenu {
    public DebuggingMenu() {
    }

    @Override
    public void client() {
        add(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Network.channel.sendToServer(new OreProfilePacket(true));
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Start Ore Profiler");
            }
        });
        add(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Network.channel.sendToServer(new OreProfilePacket(false));
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Stop Ore Profiler");
            }
        });
    }

    @Override
    public void server() {

    }
}
