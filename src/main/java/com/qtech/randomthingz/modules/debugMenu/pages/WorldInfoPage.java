package com.qtech.randomthingz.modules.debugMenu.pages;

import com.qtech.randomthingz.modules.debugMenu.DebugEntry;
import com.qtech.randomthingz.modules.debugMenu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;

import java.util.ArrayList;
import java.util.List;

public class WorldInfoPage extends DebugPage {
    public WorldInfoPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();
        ClientWorld dimension = Minecraft.getInstance().dimension;
        if (dimension == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }

        ClientWorld.ClientWorldInfo dimensionInfo = dimension.getDimensionInfo();

        int i = 0;
        list.add(new DebugEntry("spawnAngle", dimensionInfo::getSpawnAngle));
        list.add(new DebugEntry("difficulty", dimensionInfo::getDifficulty));
        list.add(new DebugEntry("dayTime", dimensionInfo::getDayTime));
        list.add(new DebugEntry("gameTime", dimensionInfo::getGameTime));
        list.add(new DebugEntry("fogDistance", dimensionInfo::getFogDistance));
        list.add(new DebugEntry("spawnX", dimensionInfo::getSpawnX));
        list.add(new DebugEntry("spawnY", dimensionInfo::getSpawnY));
        list.add(new DebugEntry("spawnZ", dimensionInfo::getSpawnZ));
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

        ClientWorld.ClientWorldInfo dimensionInfo = dimension.getDimensionInfo();

        int j = 0;
        list.add(new DebugEntry("difficultyLocked", dimensionInfo::isDifficultyLocked));
        list.add(new DebugEntry("hardcore", dimensionInfo::isHardcore));
        list.add(new DebugEntry("raining", dimensionInfo::isRaining));
        list.add(new DebugEntry("thundering", dimensionInfo::isThundering));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
