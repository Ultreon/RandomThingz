package com.ultreon.randomthingz.init;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.debug.menu.DebugGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SameParameterValue")
public class ModOverlays {
    private static final List<IIngameOverlay> OVERLAYS = new ArrayList<>();


    private static void registerTop(String name, IIngameOverlay overlay) {
        OVERLAYS.add(OverlayRegistry.registerOverlayTop(RandomThingz.MOD_ID + ":" + name, overlay));
    }

    public static void registerAll() {
        registerTop("debug_info", DebugGui.get());
    }
}
