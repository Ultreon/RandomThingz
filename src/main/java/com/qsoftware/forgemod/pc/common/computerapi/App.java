package com.qsoftware.forgemod.pc.common.computerapi;

import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class App extends Program {
    @Getter private final Computer computer;
    @Getter private final Dimension size;
    @Getter private final Point pos;
    private String[] args;

    public App(Computer computer) {
        this.computer = computer;
        this.size = this.getPreferredSize();
        this.pos = this.getPreferredPosition();
    }

    @Override
    public void execute(String... args) {
        this.args = args;
        init();
    }

    public abstract Point getPreferredPosition();

    public abstract Dimension getPreferredSize();

    public abstract void init();

    protected final String[] getArgs() {
        return args;
    }
}
