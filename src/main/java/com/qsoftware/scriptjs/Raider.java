package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.entity.monster.AbstractRaiderEntity;

public class Raider extends Patroller implements Formattable {
    private final AbstractRaiderEntity wrapper;

    protected Raider(AbstractRaiderEntity wrapper) {
        super(wrapper);

        this.wrapper = wrapper;
    }

    public boolean isCelebrating() {
        return wrapper.getCelebrating();
    }

    public int getJoinDelay() {
        return wrapper.getJoinDelay();
    }

    public Raid getRaid() {
        return new Raid(wrapper.getRaid());
    }

    public int getWave() {
        return wrapper.getWave();
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Raider", getUUID());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Raider", getUUID());
    }
}
