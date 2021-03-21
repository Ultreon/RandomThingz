package com.qsoftware.forgemod.pc.common.computerapi;

import com.qsoftware.forgemod.modules.tiles.tileentities.ComputerTileEntity;
import com.qsoftware.forgemod.pc.common.computerapi.FileSystem;
import com.qsoftware.forgemod.pc.common.computerapi.Screen;
import com.qsoftware.forgemod.pc.common.device.AbstractBios;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Computer {
    private FileSystem fileSystem;
    private Screen screen;
    private final ComputerTileEntity tileEntity;

    public Computer(ComputerTileEntity tileEntity, AbstractBios bios) {
        this.tileEntity = tileEntity;
        this.screen = bios.getScreenDriver();
    }

    public Screen getScreen() {
        return screen;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public void requestShutdown() {

    }
}
