package com.qsoftware.forgemod.pc.common.computerapi;

import com.qsoftware.forgemod.pc.common.device.Computer;

public class Folder {
    private final String path;

    public Folder(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        String[] split = path.split("/");
        return split[split.length - 1];
    }

    public void mkdirs(Computer computer) {
        computer.getFileSystem().request(FSRequest.MKDIRS, path, null);
    }
}
