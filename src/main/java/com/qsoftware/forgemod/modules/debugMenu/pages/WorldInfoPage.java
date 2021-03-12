package com.qsoftware.forgemod.modules.debugMenu.pages;

import com.qsoftware.forgemod.modules.debugMenu.DebugEntry;
import com.qsoftware.forgemod.modules.debugMenu.DebugPage;
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
        ClientWorld world = Minecraft.getInstance().world;
        if (world == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }

        ClientWorld.ClientWorldInfo worldInfo = world.getWorldInfo();

        int i = 0;
        list.add(new DebugEntry("spawnAngle", worldInfo::getSpawnAngle));
        list.add(new DebugEntry("difficulty", worldInfo::getDifficulty));
        list.add(new DebugEntry("dayTime", worldInfo::getDayTime));
        list.add(new DebugEntry("gameTime", worldInfo::getGameTime));
        list.add(new DebugEntry("fogDistance", worldInfo::getFogDistance));
        list.add(new DebugEntry("spawnX", worldInfo::getSpawnX));
        list.add(new DebugEntry("spawnY", worldInfo::getSpawnY));
        list.add(new DebugEntry("spawnZ", worldInfo::getSpawnZ));
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

        ClientWorld.ClientWorldInfo worldInfo = world.getWorldInfo();

        int j = 0;
        list.add(new DebugEntry("difficultyLocked", worldInfo::isDifficultyLocked));
        list.add(new DebugEntry("hardcore", worldInfo::isHardcore));
        list.add(new DebugEntry("raining", worldInfo::isRaining));
        list.add(new DebugEntry("thundering", worldInfo::isThundering));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
