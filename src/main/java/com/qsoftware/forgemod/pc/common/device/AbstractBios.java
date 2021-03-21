package com.qsoftware.forgemod.pc.common.device;

import com.qsoftware.forgemod.modules.tiles.tileentities.ComputerTileEntity;
import com.qsoftware.forgemod.pc.common.computerapi.Computer;
import com.qsoftware.forgemod.pc.common.computerapi.Screen;

public abstract class AbstractBios {
    private final Computer computer;
    private PowerController controller;
    private Screen screen;

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
