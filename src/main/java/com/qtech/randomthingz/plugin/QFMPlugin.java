package com.qtech.randomthingz.plugin;

@SuppressWarnings("unused")
public @interface QFMPlugin {
    String value();
    int minBuild();
    int maxBuild();
}
