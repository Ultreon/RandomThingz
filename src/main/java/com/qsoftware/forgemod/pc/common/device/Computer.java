package com.qsoftware.forgemod.pc.common.device;

import com.qsoftware.forgemod.pc.common.computerapi.FileSystem;
import com.qsoftware.forgemod.pc.common.computerapi.Screen;

public class Computer {
    private FileSystem fileSystem;
    private Screen screen;

    public Screen getScreen() {
        return screen;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }
}
