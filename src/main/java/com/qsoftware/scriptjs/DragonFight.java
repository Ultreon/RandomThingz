package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;

public class DragonFight implements Formattable {
    private net.minecraft.world.end.DragonFightManager wrapper;

    public DragonFight(net.minecraft.world.end.DragonFightManager wrapper) {
        this.wrapper = wrapper;
    }

    public int getNumAliveCrystals() {
        return this.wrapper.getNumAliveCrystals();
    }

    public void addPlayer(ServerPlayer player) {
        this.wrapper.addPlayer(player.wrapper);
    }

    public boolean hasPreviousKilledDragon() {
        return this.wrapper.hasPreviouslyKilledDragon();
    }

    public void removePlayer(ServerPlayer player) {
        this.wrapper.removePlayer(player.wrapper);
    }

    public void tryRespawnDragon() {
        this.wrapper.tryRespawnDragon();
    }
    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("DragonFight");
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("DragonFight");
    }
}
