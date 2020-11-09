package net.silentchaos512.utils.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.ConfigSpec;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.ParsingMode;
import com.electronwill.nightconfig.core.io.WritingMode;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Path;

public class ConfigSpecWrapper {
    final CommentedConfig config;
    final ConfigSpec spec;

    public ConfigSpecWrapper(CommentedConfig config, ConfigSpec spec) {
        this.config = config;
        this.spec = spec;
        load();
    }

    public static ConfigSpecWrapper create(Path filePath) {
        return new ConfigSpecWrapper(
                CommentedFileConfig.builder(filePath.toFile())
                        .parsingMode(ParsingMode.REPLACE)
                        .writingMode(WritingMode.REPLACE)
                        .autoreload()
                        .build(),
                new ConfigSpec()
        );
    }

    public ConfigValue.Builder builder(String path) {
        return ConfigValue.builder(this, path);
    }

    public void comment(String path, String comment) {
        config.setComment(path, comment);
    }

    public void comment(String path, String firstLine, String... rest) {
        StringBuilder builder = new StringBuilder(firstLine);
        for (String str : rest) builder.append("\n").append(str);
        comment(path, builder.toString());
    }

    public void validate() {
        if (!spec.isCorrect(config)) {
            String configName = getConfigName();
            LogManager.getLogger().warn("Correcting config file {}", configName);
            spec.correct(config, (action, path, incorrectValue, correctedValue) ->
                    LogManager.getLogger().warn("  {}: {} -> {}", path, incorrectValue, correctedValue));
            save();
        }
    }

    private String getConfigName() {
        return config instanceof FileConfig
                ? ((FileConfig) config).getNioPath().toString()
                : config.toString();
    }

    public final void load() {
        if (config instanceof FileConfig) {
            LogManager.getLogger().info("Loading config file {}", this::getConfigName);
            ((FileConfig) config).load();
        }
    }

    public final void save() {
        if (config instanceof FileConfig) {
            LogManager.getLogger().info("Saving config file {}", this::getConfigName);
            ((FileConfig) config).save();
        }
    }

    public CommentedConfig getConfig() {
        return config;
    }

    public ConfigSpec getSpec() {
        return spec;
    }
}
