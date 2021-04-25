package com.qtech.forgemod.modules.debug;

import com.qtech.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qtech.forgemod.modules.actionmenu.IActionMenuItem;
import com.qtech.forgemod.network.Network;
import com.qtech.forgemod.network.OreProfilePacket;
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
