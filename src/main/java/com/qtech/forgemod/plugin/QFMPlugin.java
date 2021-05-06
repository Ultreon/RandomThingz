package com.qtech.forgemod.plugin;

@SuppressWarnings("unused")
public @interface QFMPlugin {
    String value();
    int minBuild();
    int maxBuild();
}
