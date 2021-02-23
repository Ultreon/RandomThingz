package com.qsoftware.forgemod.script;

import net.minecraft.entity.player.ServerPlayerEntity;

import java.io.PrintWriter;

public class SystemOverride {
    public SystemOverride(ServerPlayerEntity player) {
        out = new ScriptPrintStream(player, false);
        err = new ScriptPrintStream(player, true);
    }

    public final PrintWriter out;
    public final PrintWriter err;
}
