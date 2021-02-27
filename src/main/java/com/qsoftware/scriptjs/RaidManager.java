package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.util.math.BlockPos;

public class RaidManager implements Formattable {
    private net.minecraft.world.raid.RaidManager wrapper;

    public RaidManager(net.minecraft.world.raid.RaidManager wrapper) {
        this.wrapper = wrapper;
    }

    public Raid get(int id) {
        return new Raid(this.wrapper.get(id));
    }

    public Raid findRaid(BlockPos pos, int distance) {
        return new Raid(this.wrapper.findRaid(pos, distance));
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("RaidManager");
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("RaidManager");
    }
}
