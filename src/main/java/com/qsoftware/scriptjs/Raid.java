package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;

public class Raid implements Formattable {
    private final net.minecraft.world.raid.Raid wrapper;

    public Raid(net.minecraft.world.raid.Raid wrapper) {
        this.wrapper = wrapper;
    }

    public int getBadOmenLevel() {
        return wrapper.getBadOmenLevel();
    }

    public int getMaxLevel() {
        return wrapper.getMaxLevel();
    }

    public BlockPos getCenter() {
        return wrapper.getCenter();
    }

    public float getCurrentHalth() {
        return wrapper.getCurrentHealth();
    }

    public float getEnchantOdds() {
        return wrapper.getEnchantOdds();
    }

    public int getGroupsSpawned() {
        return wrapper.getGroupsSpawned();
    }

    public int getId() {
        return wrapper.getId();
    }

    public Raider getLeader(int param) {
        return new Raider(wrapper.getLeader(param));
    }

    public int getRaiderCount() {
        return wrapper.getRaiderCount();
    }

    public int getWaves(Difficulty difficulty) {
        return wrapper.getWaves(difficulty);
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Raid", getId());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Raid", getId());
    }
}
