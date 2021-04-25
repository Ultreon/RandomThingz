package com.qtech.forgemod.modules.debugMenu.pages;

import com.qtech.forgemod.modules.debugMenu.DebugEntry;
import com.qtech.forgemod.modules.debugMenu.DebugPage;
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

        list.add(new DebugEntry("version", mc::getVersion));
        list.add(new DebugEntry("versionType", mc::getVersionType));
        list.add(new DebugEntry("name", mc::getName));
        list.add(new DebugEntry("forceUnicodeFont", mc::getForceUnicodeFont));
        return list;
    }

    @Override
    public List<DebugEntry> getLinesRight() {
        List<DebugEntry> list = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();

        list.add(new DebugEntry("demoMode", mc::isDemo));
        list.add(new DebugEntry("chatEnabled", mc::isChatEnabled));
        list.add(new DebugEntry("gameFocused", mc::isGameFocused));
        list.add(new DebugEntry("gamePaused", mc::isGamePaused));
        list.add(new DebugEntry("integratedServerRunning", mc::isIntegratedServerRunning));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
