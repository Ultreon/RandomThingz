package com.qsoftware.forgemod.modules.debugMenu.pages;

import com.qsoftware.forgemod.modules.debugMenu.DebugEntry;
import com.qsoftware.forgemod.modules.debugMenu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

import java.util.ArrayList;
import java.util.List;

public class WorldPage extends DebugPage {
    @Override
    public List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();
        ClientWorld world = mc.world;
        if (world == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }

        list.add(new DebugEntry("timeLightningFlash", world::getTimeLightningFlash));
        list.add(new DebugEntry("providerName", world::getProviderName));
        list.add(new DebugEntry("loadedEntities", world::getCountLoadedEntities));
        list.add(new DebugEntry("nextMapId", world::getNextMapId));
        list.add(new DebugEntry("difficulty", () -> world.getDifficulty().getDisplayName().getString()));
        list.add(new DebugEntry("seaLevel", world::getSeaLevel));
        list.add(new DebugEntry("moonPhase", () -> getMoonPhase(world.getMoonPhase())));
        list.add(new DebugEntry("spawnAngle", () -> getAngle(world.getWorldInfo().getSpawnAngle())));
        list.add(new DebugEntry("dimension", () -> world.getDimensionKey().getLocation()));
        list.add(new DebugEntry("dayTime", world::getDayTime));
        list.add(new DebugEntry("gameTime", world::getGameTime));
        list.add(new DebugEntry("cloudColor", () -> getColor(world.getCloudColor(mc.getRenderPartialTicks()))));
        if (Minecraft.getInstance().player != null) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            list.add(new DebugEntry("skyColor", () -> getColor(world.getSkyColor(player.getPosition(), mc.getRenderPartialTicks()))));
        }
        list.add(new DebugEntry("starBrightness", () -> getPercentage(world.getStarBrightness(mc.getRenderPartialTicks()))));
        list.add(new DebugEntry("sunBrightness", () -> getPercentage(world.getSunBrightness(mc.getRenderPartialTicks()))));
        return list;
    }

    @Override
    public List<DebugEntry> getLinesRight() {
        List<DebugEntry> list = new ArrayList<>();
        ClientWorld world = Minecraft.getInstance().world;
        if (world == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }

        list.add(new DebugEntry("daytime", world::isDaytime));
        list.add(new DebugEntry("nightTime", world::isNightTime));
        list.add(new DebugEntry("raining", world::isRaining));
        list.add(new DebugEntry("thundering", world::isThundering));
        list.add(new DebugEntry("saveDisabled", world::isSaveDisabled));
        if (Minecraft.getInstance().player != null) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            list.add(new DebugEntry("areaLoaded", () -> world.isAreaLoaded(player.getPosition(), 1)));
        }
        list.add(new DebugEntry("debug", world::isDebug));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
