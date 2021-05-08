package com.qtech.randomthingz.plugin;

import com.google.common.annotations.Beta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Beta
public class QFMPluginManager {
    private static final QFMPluginManager instance = new QFMPluginManager();
    private final List<ModPlugin> plugins = new ArrayList<>();

    public static QFMPluginManager get() {
        return instance;
    }

    public QFMPluginManager() {
    }

    public void registerPlugin(Class<?> clazz) {
        this.plugins.add(new ModPlugin(clazz));
    }

    public List<ModPlugin> getPlugins() {
        return Collections.unmodifiableList(plugins);
    }
}
