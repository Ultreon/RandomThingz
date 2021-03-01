package com.qsoftware.forgemod.script;

import net.minecraft.client.entity.player.ClientPlayerEntity;

import java.io.PrintWriter;

public class ClientScriptJSPrintWriter extends PrintWriter {
    public ClientScriptJSPrintWriter(Language language, ClientPlayerEntity player, boolean isErr) {
        super(new ClientScriptJSOutputWriter(language, player, isErr), true);
    }
}
