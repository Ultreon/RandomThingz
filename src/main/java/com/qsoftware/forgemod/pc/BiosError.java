package com.qsoftware.forgemod.pc;

public abstract class BiosError {
    public boolean canContinue() {
        return false;
    }

    public abstract int getTicksBeforeAutoRestart();
}
