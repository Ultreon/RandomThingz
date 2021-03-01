package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;

public class SoundEvent implements Formattable {
    public net.minecraft.util.SoundEvent wrapper;

    public SoundEvent(net.minecraft.util.SoundEvent wrapper) {
        this.wrapper = wrapper;
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("SoundEvent", wrapper.getRegistryName());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("SoundEvent", wrapper.getRegistryName());
    }
}
