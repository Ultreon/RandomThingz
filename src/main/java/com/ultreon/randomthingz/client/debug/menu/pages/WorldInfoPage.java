package com.ultreon.randomthingz.client.debug.menu.pages;

import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

import java.util.ArrayList;
import java.util.List;

public class WorldInfoPage extends DebugPage {
    public WorldInfoPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();
        ClientLevel dimension = Minecraft.getInstance().level;
        if (dimension == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }

        ClientLevel.ClientLevelData dimensionInfo = dimension.getLevelData();

        int i = 0;
        list.add(new DebugEntry("spawnAngle", dimensionInfo::getSpawnAngle));
        list.add(new DebugEntry("difficulty", dimensionInfo::getDifficulty));
        list.add(new DebugEntry("dayTime", dimensionInfo::getDayTime));
        list.add(new DebugEntry("gameTime", dimensionInfo::getGameTime));
        list.add(new DebugEntry("fogDistance", dimensionInfo::getClearColorScale));
        list.add(new DebugEntry("spawnX", dimensionInfo::getXSpawn));
        list.add(new DebugEntry("spawnY", dimensionInfo::getYSpawn));
        list.add(new DebugEntry("spawnZ", dimensionInfo::getZSpawn));
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

        ClientLevel.ClientLevelData dimensionInfo = dimension.getLevelData();

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
