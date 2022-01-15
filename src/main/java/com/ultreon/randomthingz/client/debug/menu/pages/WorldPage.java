package com.ultreon.randomthingz.client.debug.menu.pages;

import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

import java.util.ArrayList;
import java.util.List;

public class WorldPage extends DebugPage {
    public WorldPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();
        ClientLevel dimension = mc.level;
        if (dimension == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }

        list.add(new DebugEntry("timeLightningFlash", dimension::getSkyFlashTime));
        list.add(new DebugEntry("providerName", dimension::gatherChunkSourceStats));
        list.add(new DebugEntry("loadedEntities", dimension::getEntityCount));
        list.add(new DebugEntry("nextMapId", dimension::getFreeMapId));
        list.add(new DebugEntry("difficulty", () -> dimension.getDifficulty().getDisplayName().getString()));
        list.add(new DebugEntry("seaLevel", dimension::getSeaLevel));
        list.add(new DebugEntry("moonPhase", () -> getMoonPhase(dimension.getMoonPhase())));
        list.add(new DebugEntry("spawnAngle", () -> getAngle(dimension.getLevelData().getSpawnAngle())));
        list.add(new DebugEntry("dimension", () -> dimension.dimension().location()));
        list.add(new DebugEntry("dayTime", dimension::getDayTime));
        list.add(new DebugEntry("gameTime", dimension::getGameTime));
        list.add(new DebugEntry("cloudColor", () -> getColor(dimension.getCloudColor(mc.getFrameTime()))));
        if (Minecraft.getInstance().player != null) {
            LocalPlayer player = Minecraft.getInstance().player;
            list.add(new DebugEntry("skyColor", () -> getColor(dimension.getSkyColor(player.blockPosition(), mc.getFrameTime()))));
        }
        list.add(new DebugEntry("starBrightness", () -> getPercentage(dimension.getStarBrightness(mc.getFrameTime()))));
        list.add(new DebugEntry("sunBrightness", () -> getPercentage(dimension.getSkyDarken(mc.getFrameTime()))));
        return list;
    }

    @Override
    public List<DebugEntry> getLinesRight() {
        List<DebugEntry> list = new ArrayList<>();
        ClientLevel dimension = Minecraft.getInstance().level;
        if (dimension == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }

        list.add(new DebugEntry("daytime", dimension::isDay));
        list.add(new DebugEntry("nightTime", dimension::isNight));
        list.add(new DebugEntry("raining", dimension::isRaining));
        list.add(new DebugEntry("thundering", dimension::isThundering));
        list.add(new DebugEntry("saveDisabled", dimension::noSave));
        if (Minecraft.getInstance().player != null) {
            LocalPlayer player = Minecraft.getInstance().player;
            list.add(new DebugEntry("areaLoaded", () -> dimension.isAreaLoaded(player.blockPosition(), 1)));
        }
        list.add(new DebugEntry("debug", dimension::isDebug));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
