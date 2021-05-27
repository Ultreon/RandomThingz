package com.qtech.randomthingz.modules.debugMenu.pages;

import com.qtech.randomthingz.modules.debugMenu.DebugEntry;
import com.qtech.randomthingz.modules.debugMenu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

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
        ClientWorld dimension = mc.dimension;
        if (dimension == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }

        list.add(new DebugEntry("timeLightningFlash", dimension::getTimeLightningFlash));
        list.add(new DebugEntry("providerName", dimension::getProviderName));
        list.add(new DebugEntry("loadedEntities", dimension::getCountLoadedEntities));
        list.add(new DebugEntry("nextMapId", dimension::getNextMapId));
        list.add(new DebugEntry("difficulty", () -> dimension.getDifficulty().getDisplayName().getString()));
        list.add(new DebugEntry("seaLevel", dimension::getSeaLevel));
        list.add(new DebugEntry("moonPhase", () -> getMoonPhase(dimension.getMoonPhase())));
        list.add(new DebugEntry("spawnAngle", () -> getAngle(dimension.getDimensionInfo().getSpawnAngle())));
        list.add(new DebugEntry("dimension", () -> dimension.getDimensionKey().getLocation()));
        list.add(new DebugEntry("dayTime", dimension::getDayTime));
        list.add(new DebugEntry("gameTime", dimension::getGameTime));
        list.add(new DebugEntry("cloudColor", () -> getColor(dimension.getCloudColor(mc.getRenderPartialTicks()))));
        if (Minecraft.getInstance().player != null) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            list.add(new DebugEntry("skyColor", () -> getColor(dimension.getSkyColor(player.getPosition(), mc.getRenderPartialTicks()))));
        }
        list.add(new DebugEntry("starBrightness", () -> getPercentage(dimension.getStarBrightness(mc.getRenderPartialTicks()))));
        list.add(new DebugEntry("sunBrightness", () -> getPercentage(dimension.getSunBrightness(mc.getRenderPartialTicks()))));
        return list;
    }

    @Override
    public List<DebugEntry> getLinesRight() {
        List<DebugEntry> list = new ArrayList<>();
        ClientWorld dimension = Minecraft.getInstance().dimension;
        if (dimension == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }

        list.add(new DebugEntry("daytime", dimension::isDaytime));
        list.add(new DebugEntry("nightTime", dimension::isNightTime));
        list.add(new DebugEntry("raining", dimension::isRaining));
        list.add(new DebugEntry("thundering", dimension::isThundering));
        list.add(new DebugEntry("saveDisabled", dimension::isSaveDisabled));
        if (Minecraft.getInstance().player != null) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            list.add(new DebugEntry("areaLoaded", () -> dimension.isAreaLoaded(player.getPosition(), 1)));
        }
        list.add(new DebugEntry("debug", dimension::isDebug));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
