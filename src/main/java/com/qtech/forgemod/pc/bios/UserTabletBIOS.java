package com.qtech.forgemod.pc.bios;

public abstract class UserTabletBIOS extends UserBIOS {
    @Override
    protected abstract void sleep();

    @Override
    protected abstract void restart();

    @Override
    protected abstract void shutdown();

    @Override
    protected boolean supportsSleep() {
        return true;
    }

    @Override
    protected abstract boolean supportsRestart();

    @Override
    protected boolean supportsShutdown() {
        return true;
    }
}
