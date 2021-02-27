package com.qsoftware.forgemod.script.js;

import net.minecraft.client.entity.player.ClientPlayerEntity;

import java.io.PrintWriter;

public class ClientSystemOverride extends SystemOverride {
    public ClientSystemOverride(ClientPlayerEntity player) {
        out = new ClientScriptJSPrintWriter(player, false);
        err = new ClientScriptJSPrintWriter(player, true);
    }

    public final PrintWriter out;
    public final PrintWriter err;
}
