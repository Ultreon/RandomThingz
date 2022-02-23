package com.ultreon.randomthingz.plugin;

import com.google.common.annotations.Beta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Beta
public class RTPluginManager {
    private static final RTPluginManager instance = new RTPluginManager();
    private final List<ModPlugin> plugins = new ArrayList<>();

    public static RTPluginManager get() {
        return instance;
    }

    public RTPluginManager() {
    }

    public void registerPlugin(Class<?> clazz) {
        this.plugins.add(new ModPlugin(clazz));
    }

    public List<ModPlugin> getPlugins() {
        return Collections.unmodifiableList(plugins);
    }
}
