package com.qsoftware.forgemod.script;

import com.google.common.base.Charsets;
import com.qsoftware.forgemod.modules.pcShutdown.PCShutdownModule;
import lombok.Getter;
import net.minecraft.entity.player.PlayerEntity;
import org.jline.reader.History;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.nio.charset.Charset;

@Deprecated
public class ScriptInstance {
    @Getter private final PlayerEntity player;
    @Getter private History history;

    ScriptInstance(PlayerEntity player) {
        try {
            history = new DefaultHistory(new LineReaderImpl(TerminalBuilder.builder()
                    .exec(true).encoding(Charsets.US_ASCII).size(new Size(65536, 128)).jansi(false).jna(false).system(false).name("Minecraft_" + player.getName()).build()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.player = player;
    }

    public void execute(String command) {
        history.add(command);
    }
}
