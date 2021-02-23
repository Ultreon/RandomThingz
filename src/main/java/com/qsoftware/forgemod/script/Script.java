package com.qsoftware.forgemod.script;

import com.qsoftware.forgemod.QForgeMod;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.script.ScriptEngine;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
public class Script {
    private static final Logger LOGGER = LogManager.getLogger("QFM:Script");
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

    private final ScriptEngine engine;

    @NotNull
    private static File dir() {
        return new File(ServerScriptUtils.getServer().getFile(QForgeMod.getModId()), "scripts");
    }

    Script(ScriptEngine engine) {
        this.engine = engine;
    }

    @SneakyThrows
    public void runFile(String file, String... args) {
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
            engine.eval(new String(cache));
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
}
