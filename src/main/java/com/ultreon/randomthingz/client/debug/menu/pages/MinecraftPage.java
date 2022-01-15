package com.ultreon.randomthingz.client.debug.menu.pages;

import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class MinecraftPage extends DebugPage {
    public MinecraftPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();

        list.add(new DebugEntry("version", mc::getLaunchedVersion));
        list.add(new DebugEntry("versionType", mc::getVersionType));
        list.add(new DebugEntry("name", mc::name));
        list.add(new DebugEntry("forceUnicodeFont", mc::isEnforceUnicode));
        return list;
    }

    @Override
    public List<DebugEntry> getLinesRight() {
        List<DebugEntry> list = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();

        list.add(new DebugEntry("demoMode", mc::isDemo));
        list.add(new DebugEntry("chatEnabled", mc::allowsChat));
        list.add(new DebugEntry("gameFocused", mc::isWindowActive));
        list.add(new DebugEntry("gamePaused", mc::isPaused));
        list.add(new DebugEntry("integratedServerRunning", mc::isLocalServer));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
