package com.qsoftware.forgemod.script.js;

import net.minecraft.entity.player.ServerPlayerEntity;

import java.io.PrintWriter;

public class SystemOverride {
    public SystemOverride(ServerPlayerEntity player) {
        out = new ScriptJSPrintStream(player, false);
        err = new ScriptJSPrintStream(player, true);
    }

    public final PrintWriter out;
    public final PrintWriter err;
}
