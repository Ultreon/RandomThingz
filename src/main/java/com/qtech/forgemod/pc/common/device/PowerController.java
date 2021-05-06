package com.qtech.forgemod.pc.common.device;

import com.qtech.forgemod.pc.common.computerapi.Computer;

public abstract class PowerController {
    boolean state;

    private final Computer computer;
    private SecureBios bios;

    protected PowerController(Computer computer) {
        this.computer = computer;
    }

    public abstract void tick();
    public final void on() {
        state = true;
        this.bios.boot();
    }
    public final void off() {
        this.bios.requestShutdown();
        state = false;
    }
    public final void forceOff() {
        this.bios.forceShutdown();
        state = false;
    }
}
