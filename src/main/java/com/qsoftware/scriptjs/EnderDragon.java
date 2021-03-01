package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;

public class EnderDragon extends LivingBeing implements Formattable {
    private final EnderDragonEntity wrapper;

    protected EnderDragon(EnderDragonEntity wrapper) {
        super(wrapper);
        this.wrapper = wrapper;
    }

    public DragonFight getFightManager() {
        return new DragonFight(this.wrapper.getFightManager());
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("EnderDragon", getUUID());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("EnderDragon", getUUID());
    }
}
