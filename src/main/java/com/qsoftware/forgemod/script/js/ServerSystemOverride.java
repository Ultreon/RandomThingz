package com.qsoftware.forgemod.script.js;

import net.minecraft.entity.player.ServerPlayerEntity;

import java.io.PrintWriter;

public class ServerSystemOverride extends SystemOverride {
    public ServerSystemOverride(ServerPlayerEntity player) {
        out = new ServerScriptJSPrintWriter(player, false);
        err = new ServerScriptJSPrintWriter(player, true);
    }

    public final PrintWriter out;
    public final PrintWriter err;
}
