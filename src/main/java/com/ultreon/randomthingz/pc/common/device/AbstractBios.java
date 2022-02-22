package com.ultreon.randomthingz.pc.common.device;

import com.ultreon.randomthingz.pc.common.computerapi.Computer;
import com.ultreon.randomthingz.pc.common.computerapi.Screen;

public abstract class AbstractBios {
    private final Computer computer;
    private final PowerController controller;
    private final Screen screen;

    public AbstractBios(Computer computer, PowerController controller) {
        this.computer = computer;
        this.controller = controller;
        this.screen = new Screen();
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
