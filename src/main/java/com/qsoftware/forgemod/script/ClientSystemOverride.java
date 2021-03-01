package com.qsoftware.forgemod.script;

import net.minecraft.client.entity.player.ClientPlayerEntity;

import java.io.PrintWriter;

public class ClientSystemOverride extends SystemOverride {
    public ClientSystemOverride(Language language, ClientPlayerEntity player) {
        this.out = new ClientScriptJSPrintWriter(language, player, false);
        this.err = new ClientScriptJSPrintWriter(language, player, true);
    }

    public final PrintWriter out;
    public final PrintWriter err;
}
