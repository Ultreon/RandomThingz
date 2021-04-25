package com.qtech.forgemod.pc.common.computerapi;

public class File {
    private final String path;

    public File(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        String[] split = path.split("/");
        return split[split.length - 1];
    }

    public String getExt() {
        String[] split = getName().split(".");
        return "." + split[split.length - 1];
    }

    public boolean isExecutable() {
        return getExt().equalsIgnoreCase(".qfmx");
    }
}
