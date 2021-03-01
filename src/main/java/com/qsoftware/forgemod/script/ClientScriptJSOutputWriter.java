package com.qsoftware.forgemod.script;

import com.qsoftware.forgemod.script.ui.ScriptJSClientGui;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;
import java.io.Writer;

public class ClientScriptJSOutputWriter extends Writer {
    private char[] cache = new char[65536];
    private final ClientPlayerEntity player;
    private final boolean isErr;
    private Language language;

    public ClientScriptJSOutputWriter(Language language, ClientPlayerEntity player, boolean isErr) {
        this.language = language;
        this.player = player;
        this.isErr = isErr;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        if (len >= 0) System.arraycopy(cbuf, 0, cache, off, len - 1);
    }

    @Override
    public void flush() {
        if (language == Language.JS) {
            ScriptJSClientGui.getInstance().printScriptMessage(new StringTextComponent((isErr ? TextFormatting.RED.toString() : "") + new String(cache).replaceAll("\u0000", "")));
        }
        cache = new char[]{};
    }

    @Override
    public void close() throws IOException {
        throw new IOException("Closing script output is not allowed.");
    }
}
