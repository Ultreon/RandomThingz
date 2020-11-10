package com.qsoftware.silent.utils.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.ConfigSpec;

import java.util.function.Consumer;

public class BooleanValue extends ConfigValue<Boolean> {
    BooleanValue(ConfigSpecWrapper wrapper, String path, Consumer<ConfigSpec> handleSpec, Consumer<CommentedConfig> handleConfig) {
        super(wrapper, path, handleSpec, handleConfig);
    }

    public static Builder builder(ConfigSpecWrapper wrapper, String path) {
        return new Builder(wrapper, path);
    }
}
