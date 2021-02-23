package com.qsoftware.forgemod.script;

import com.qsoftware.forgemod.modules.debugMenu.DebugMenu;
import com.qsoftware.forgemod.network.Network;
import com.qsoftware.forgemod.network.ScriptResponsePacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;

public class ScriptOutputWriter extends Writer {
    private char[] cache = new char[65536];
    private final ServerPlayerEntity player;
    private boolean isErr;

    public ScriptOutputWriter(ServerPlayerEntity player, boolean isErr) {

        this.player = player;
        this.isErr = isErr;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        if (len >= 0) System.arraycopy(cbuf, 0, cache, off, len - 1);
    }

    @Override
    public void flush() {
        Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket((isErr ? TextFormatting.RED.toString() : "") + new String(cache)));
        cache = new char[]{};
    }

    @Override
    public void close() throws IOException {
        throw new IOException("Closing script output is not allowed.");
    }
}
