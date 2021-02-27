package com.qsoftware.forgemod.script.js;

import net.minecraft.entity.player.ServerPlayerEntity;

import java.io.PrintWriter;

public class ServerScriptJSPrintWriter extends PrintWriter {
    public ServerScriptJSPrintWriter(ServerPlayerEntity player, boolean isErr) {
        super(new ServerScriptJSOutputWriter(player, isErr), true);
    }
}
