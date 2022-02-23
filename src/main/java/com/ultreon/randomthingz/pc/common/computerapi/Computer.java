package com.ultreon.randomthingz.pc.common.computerapi;

import com.ultreon.randomthingz.pc.common.device.AbstractBios;
import com.ultreon.randomthingz.block.entity.ComputerTileEntity;

public abstract class Computer {
    private AbstractFileSystem fileSystem;
    private final Screen screen;
    protected final ComputerTileEntity tileEntity;

    public Computer(ComputerTileEntity tileEntity) {
        this.tileEntity = tileEntity;
        this.screen = createBios().getScreenDriver();
    }

    public Screen getScreen() {
        return screen;
    }

    public AbstractFileSystem getFileSystem() {
        return fileSystem;
    }

    public void requestShutdown() {

    }

    public abstract AbstractBios createBios();

    public void tick() {

    }

    public Screen getOrCreateScreen() {
        return screen;
    }
}
