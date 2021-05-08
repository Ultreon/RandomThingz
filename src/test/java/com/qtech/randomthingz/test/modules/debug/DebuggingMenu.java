package com.qtech.randomthingz.test.modules.debug;

import com.qtech.randomthingz.modules.actionmenu.AbstractActionMenu;
import com.qtech.randomthingz.modules.actionmenu.IActionMenuItem;
import com.qtech.randomthingz.network.Network;
import com.qtech.randomthingz.test.network.OreProfilePacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DebuggingMenu extends AbstractActionMenu {
    public DebuggingMenu() {
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                Network.channel.sendToServer(new OreProfilePacket(true));
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Start Ore Profiler");
            }
        });
        addItem(new IActionMenuItem() {
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
}
