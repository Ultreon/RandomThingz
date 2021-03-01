package com.qsoftware.forgemod.script;

@FunctionalInterface
public interface RunFile {
    void run(String file, String... args);
}
