package com.qsoftware.forgemod.pc.common.computerapi;

public abstract class Application extends Executable {
    private String[] args;

    public abstract void init();

    @Override
    public final void execute(String... args) {
        this.args = args;
        init();
    }

    protected final String[] getArgs() {
        return args;
    }
}
