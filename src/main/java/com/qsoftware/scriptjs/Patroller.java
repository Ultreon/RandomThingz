package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.entity.monster.PatrollerEntity;
import net.minecraft.util.math.BlockPos;

public class Patroller extends Monster implements Formattable {
    private final PatrollerEntity wrapper;

    protected Patroller(PatrollerEntity wrapper) {
        super(wrapper);

        this.wrapper = wrapper;
    }

    public BlockPos getPatrolTarget() {
        return wrapper.getPatrolTarget();
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Patroller", getUUID());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Patroller", getUUID());
    }
}
