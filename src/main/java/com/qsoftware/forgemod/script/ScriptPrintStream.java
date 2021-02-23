package com.qsoftware.forgemod.script;

import net.minecraft.entity.player.ServerPlayerEntity;

import java.io.PrintWriter;

public class ScriptPrintStream extends PrintWriter {
    public ScriptPrintStream(ServerPlayerEntity player, boolean isErr) {
        super(new ScriptOutputWriter(player, isErr));
    }
}
