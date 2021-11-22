package com.ultreon.randomthingz.pc;

public abstract class BiosError {
    public boolean canContinue() {
        return false;
    }

    public abstract int getTicksBeforeAutoRestart();
}
