package com.qtech.randomthingz.pc.bios;

public abstract class MilitaryBIOS extends BIOS {
    @Override
    protected abstract void sleep();

    @Override
    protected abstract void restart();

    @Override
    protected abstract void shutdown();

    @Override
    protected abstract void selfDestruct();

    @Override
    protected boolean supportsSleep() {
        return true;
    }

    @Override
    protected boolean supportsRestart() {
        return true;
    }

    @Override
    protected boolean supportsShutdown() {
        return true;
    }

    @Override
    protected abstract boolean supportsSelfDestruct();
}
