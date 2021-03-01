package com.qsoftware.forgemod.script;

import com.qsoftware.forgemod.common.interfaces.Formattable;

public abstract class SystemOverride implements Formattable {
    @Override
    public final String toFormattedString() {
        return CommonScriptJSUtils.formatClass("System");
    }

    public final String toString() {
        return CommonScriptJSUtils.formatClassRaw("System");
    }
}
