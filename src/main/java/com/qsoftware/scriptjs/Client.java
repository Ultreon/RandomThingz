package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.client.Minecraft;

public class Client implements Formattable {
    private final Minecraft wrapper;

    public Client() {
        this.wrapper = Minecraft.getInstance();
    }

    public Server getIntegratedServer() {
        return new Server(wrapper.getIntegratedServer());
    }

    public GameWindow getMainWindow() {
        return new GameWindow(wrapper.getMainWindow());
    }

    public ClientWorld getWorld() {
        return new ClientWorld(wrapper.world);
    }

    public ClientPlayer getPlayer() {
        return new ClientPlayer(wrapper.player);
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Client");
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Client");
    }
}
