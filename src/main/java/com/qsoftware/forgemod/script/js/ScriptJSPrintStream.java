package com.qsoftware.forgemod.script.js;

import net.minecraft.entity.player.ServerPlayerEntity;

import java.io.PrintWriter;

public class ScriptJSPrintStream extends PrintWriter {
    public ScriptJSPrintStream(ServerPlayerEntity player, boolean isErr) {
        super(new ScriptJSOutputWriter(player, isErr), true);
    }
}
