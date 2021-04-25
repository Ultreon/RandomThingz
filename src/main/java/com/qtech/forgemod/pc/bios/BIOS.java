package com.qtech.forgemod.pc.bios;

public abstract class BIOS {
    protected void sleep() {

    }

    protected void restart() {

    }

    protected void shutdown() {

    }

    protected void selfDestruct() {

    }

    protected abstract boolean supportsSleep();

    protected abstract boolean supportsRestart();

    protected abstract boolean supportsShutdown();

    protected abstract boolean supportsSelfDestruct();
}
