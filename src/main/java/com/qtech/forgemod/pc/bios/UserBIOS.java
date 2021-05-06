package com.qtech.forgemod.pc.bios;

public abstract class UserBIOS extends BIOS {
    @Override
    protected final void selfDestruct() {

    }

    @Override
    protected abstract boolean supportsSleep();

    @Override
    protected abstract boolean supportsRestart();

    @Override
    protected abstract boolean supportsShutdown();

    @Override
    protected final boolean supportsSelfDestruct() {
        return false;
    }
}
