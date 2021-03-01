package com.qsoftware.forgemod.script;

import net.minecraft.entity.player.ServerPlayerEntity;

import java.io.PrintWriter;

public class ServerSystemOverride extends SystemOverride {
    public ServerSystemOverride(Language language, ServerPlayerEntity player) {
        this.out = new ServerScriptJSPrintWriter(language, player, false);
        this.err = new ServerScriptJSPrintWriter(language, player, true);
    }

    public final PrintWriter out;
    public final PrintWriter err;
}
