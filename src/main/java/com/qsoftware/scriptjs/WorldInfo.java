package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.storage.IWorldInfo;

@SuppressWarnings("unused")
public class WorldInfo implements Formattable {
    private final IWorldInfo wrapper;

    public WorldInfo(IWorldInfo wrapper) {
        this.wrapper = wrapper;
    }

    public long getDayTime() {
        return wrapper.getDayTime() % 24000;
    }

    public Difficulty getDifficulty() {
        return wrapper.getDifficulty();
    }

    public long getGameTime() {
        return wrapper.getGameTime();
    }

    public GameRules getGameRules() {
        return wrapper.getGameRulesInstance();
    }

    public float getSpawnAngle() {
        return wrapper.getSpawnAngle();
    }

    public Vector3i getSpawn() {
        return new Vector3i(wrapper.getSpawnX(), wrapper.getSpawnY(), wrapper.getSpawnZ());
    }

    public int getSpawnX() {
        return wrapper.getSpawnX();
    }

    public int getSpawnY() {
        return wrapper.getSpawnY();
    }

    public int getSpawnZ() {
        return wrapper.getSpawnZ();
    }

    public boolean isDifficultyLocked() {
        return wrapper.isDifficultyLocked();
    }

    public boolean isHardcore() {
        return wrapper.isHardcore();
    }

    public boolean isRaining() {
        return wrapper.isRaining();
    }

    public boolean isThundering() {
        return wrapper.isThundering();
    }

    public void setRaining(boolean raining) {
        wrapper.setRaining(raining);
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("WorldInfo");
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("WorldInfo");
    }
}
