package com.qsoftware.forgemod.script;

import com.google.common.base.Charsets;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.interfaces.Formattable;
import lombok.SneakyThrows;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.WriterOutputStream;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
public class ScriptJS implements Formattable {
    private static final Logger LOGGER = LogManager.getLogger("QFM:ScriptJS");
    private static final File scriptDir;

    static {
        File dir = dir();

        if (!dir.exists()) {
            dir.mkdirs();
        } else if (dir.isFile()) {
            dir.delete();
            dir.mkdirs();
        }

        scriptDir = dir;
    }

    private final AbstractScriptInstance scriptJS;

    @NotNull
    private static File dir() {
        return new File(ServerScriptJSUtils.getServer().getFile(QForgeMod.getModId()), "scripts");
    }

    ScriptJS(AbstractScriptInstance scriptJS) {
        this.scriptJS = scriptJS;
    }

    public void runFile(String file, String... args) {
        if (scriptJS instanceof ClientScriptInstance) {
            ClientScriptInstance scriptJS = (ClientScriptInstance) this.scriptJS;
            ClientScriptInstance subInstance = new ClientScriptInstance(Language.JS, (ClientPlayerEntity) scriptJS.getPlayer(),
                ScriptManager.newContext(Dist.CLIENT)
                    .out(new WriterOutputStream(new ClientSystemOverride(Language.JS, (ClientPlayerEntity) scriptJS.getPlayer()).out, Charsets.UTF_8))
                    .err(new WriterOutputStream(new ClientSystemOverride(Language.JS, (ClientPlayerEntity) scriptJS.getPlayer()).err, Charsets.UTF_8)).build());
            this.run(subInstance, file, args);
        }
        if (scriptJS instanceof ServerScriptInstance) {
            ServerScriptInstance scriptJS = (ServerScriptInstance) this.scriptJS;
            ServerScriptInstance subInstance = new ServerScriptInstance(Language.JS, (ServerPlayerEntity) scriptJS.getPlayer(),
                ScriptManager.newContext(Dist.DEDICATED_SERVER)
                    .out(new WriterOutputStream(new ServerSystemOverride(Language.JS, (ServerPlayerEntity) scriptJS.getPlayer()).out, Charsets.UTF_8))
                    .err(new WriterOutputStream(new ServerSystemOverride(Language.JS, (ServerPlayerEntity) scriptJS.getPlayer()).err, Charsets.UTF_8)).build());
            this.run(subInstance, file, args);
        }
    }

    public void importFile(String file) {
        this.run(this.scriptJS, file);
    }

    @SneakyThrows
    void run(AbstractScriptInstance scriptJS, String file, String... args) {
        if (new File(file).isAbsolute()) {
            throw new IllegalArgumentException("Given file is absolute, expected an relative path.");
        }

        // Get script file.
        File file1 = new File(scriptDir, file);

        // Create file reader.
        FileReader fileReader = new FileReader(file1);
        try {
            // Create initial char cache instance.
            char[] cache = new char[(Math.toIntExact(file1.length()))];

            // Read data.
            fileReader.read(cache, 0, Math.toIntExact(file1.length()));

            // Create string instance of char cache.
            String code = new String(cache);

            // Dump script data.
            LOGGER.debug("// START SCRIPT " + file + " //");
            for (String line : code.split("(\r\n|\r|\n)")) {
                QForgeMod.LOGGER.debug(line);
            }
            LOGGER.debug("// END SCRIPT " + file + " //");

            // Evaluate code using the script engine.
            scriptJS.eval(scriptJS.getLanguage(), new String(cache));
        } catch (Exception e) {
            // ERROR!!!
            e.printStackTrace();
        }

        fileReader.close();
    }

    //Read file content into the string with - Files.lines(Path path, Charset cs)
    private static String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    @Override
    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("ScriptJS");
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("ScriptJS");
    }
}
