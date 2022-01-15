package com.ultreon.randomthingz.pc.common.device;

import com.ultreon.randomthingz.pc.common.computerapi.Computer;
import com.ultreon.randomthingz.pc.common.computerapi.Screen;
import com.ultreon.randomthingz.tileentity.ComputerTileEntity;

public abstract class AbstractBios {
    private final Computer computer;
    private final PowerController controller;
    private final Screen screen;

    public AbstractBios(ComputerTileEntity te, PowerController controller) {
        this.controller = controller;
        this.screen = new Screen();
        this.computer = new Computer(te, this);
    }

    public final void shutdown() {
        this.controller.state = false;
    }

    public final void requestShutdown() {
        this.computer.requestShutdown();
    }

    public Screen getScreenDriver() {
        return this.screen;
    }
}
