package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;

public class ClientWorldInfo extends WorldInfo implements Formattable {
    private final ClientWorld.ClientWorldInfo wrapper;

    public ClientWorldInfo(ClientWorld.ClientWorldInfo wrapper) {
        super(wrapper);
        this.wrapper = wrapper;
    }

    public double getVoidFogHeight() {
        return wrapper.getVoidFogHeight();
    }

    public void setDayTime(long time) {
        wrapper.setDayTime(time % 24000);
    }

    public void setDifficulty(Difficulty difficulty) {
        wrapper.setDifficulty(difficulty);
    }

    public void setDifficultyLocked(boolean locked) {
        wrapper.setDifficultyLocked(locked);
    }

    public void setGameTime(long time) {
        wrapper.setGameTime(time);
    }

    public void setSpawn(BlockPos spawnPoint, float angle) {
        wrapper.setSpawn(spawnPoint, angle);
    }

    public void setSpawnAngle(float angle) {
        wrapper.setSpawnAngle(angle);
    }

    public void setSpawnX(int x) {
        wrapper.setSpawnX(x);
    }

    public void setSpawnY(int y) {
        wrapper.setSpawnY(y);
    }

    public void setSpawnZ(int z) {
        wrapper.setSpawnZ(z);
    }
    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("ClientWorldInfo");
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("ClientWorldInfo");
    }
}
