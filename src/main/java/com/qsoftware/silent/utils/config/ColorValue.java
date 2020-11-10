package com.qsoftware.silent.utils.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.ConfigSpec;
import com.qsoftware.silent.utils.Color;

import java.util.function.Consumer;

public class ColorValue extends ConfigValue<Color> {
    private final int defaultValue;

    ColorValue(int defaultValue, ConfigSpecWrapper wrapper, String path, Consumer<ConfigSpec> handleSpec, Consumer<CommentedConfig> handleConfig) {
        super(wrapper, path, handleSpec, handleConfig);
        this.defaultValue = defaultValue;
    }

    @Override
    public Color get() {
        return Color.tryParse(config.get(path), defaultValue);
    }

    public static Builder builder(ConfigSpecWrapper wrapper, String path) {
        return new Builder(wrapper, path);
    }
}
