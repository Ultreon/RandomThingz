package com.ultreon.randomthingz.pc.common.computerapi;

import com.ultreon.randomthingz.pc.common.device.AbstractBios;
import com.ultreon.randomthingz.tileentity.ComputerTileEntity;

public class Computer {
    private AbstractFileSystem fileSystem;
    private final Screen screen;
    private final ComputerTileEntity tileEntity;

    public Computer(ComputerTileEntity tileEntity, AbstractBios bios) {
        this.tileEntity = tileEntity;
        this.screen = bios.getScreenDriver();
    }

    public Screen getScreen() {
        return screen;
    }

    public AbstractFileSystem getFileSystem() {
        return fileSystem;
    }

    public void requestShutdown() {

    }
}
