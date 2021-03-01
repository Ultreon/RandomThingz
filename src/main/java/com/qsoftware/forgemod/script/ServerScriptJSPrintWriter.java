package com.qsoftware.forgemod.script;

import net.minecraft.entity.player.ServerPlayerEntity;

import java.io.PrintWriter;

public class ServerScriptJSPrintWriter extends PrintWriter {
    public ServerScriptJSPrintWriter(Language language, ServerPlayerEntity player, boolean isErr) {
        super(new ServerScriptJSOutputWriter(language, player, isErr), true);
    }
}
