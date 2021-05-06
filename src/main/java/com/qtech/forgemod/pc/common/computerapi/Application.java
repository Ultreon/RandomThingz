package com.qtech.forgemod.pc.common.computerapi;

public abstract class Application extends Executable {
    private String[] args;

    public abstract void initialize();

    @Override
    public final void execute(String... args) {
        this.args = args;
        initialize();
    }

    protected final String[] getArgs() {
        return args;
    }
}
