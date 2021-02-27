package com.qsoftware.forgemod.script.js;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.io.PrintWriter;

public class ClientScriptJSPrintWriter extends PrintWriter {
    public ClientScriptJSPrintWriter(ClientPlayerEntity player, boolean isErr) {
        super(new ClientScriptJSOutputWriter(player, isErr), true);
    }
}
