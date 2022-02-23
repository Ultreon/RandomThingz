package com.ultreon.randomthingz.plugin;

@SuppressWarnings("unused")
public @interface RTPlugin {
    String value();

    int minBuild();

    int maxBuild();
}
