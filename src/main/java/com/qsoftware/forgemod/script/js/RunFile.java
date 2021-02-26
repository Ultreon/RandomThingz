package com.qsoftware.forgemod.script.js;

@FunctionalInterface
public interface RunFile {
    void run(String file, String... args);
}
