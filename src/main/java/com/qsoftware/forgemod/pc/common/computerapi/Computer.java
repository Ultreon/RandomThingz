package com.qsoftware.forgemod.pc.common.computerapi;

import com.qsoftware.forgemod.modules.tiles.tileentities.ComputerTileEntity;
import com.qsoftware.forgemod.pc.common.device.AbstractBios;

public class Computer {
    private AbstractFileSystem fileSystem;
    private Screen screen;
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
