package com.ultreon.randomthingz.core;

import org.jetbrains.annotations.Nullable;

import java.time.Duration;

class SystemBatteryDummy implements SystemBattery {
    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public ACLineStatus getACLineStatus() {
        return ACLineStatus.UNKNOWN;
    }

    @Override
    public BatteryFlag getBatteryFlag() {
        return BatteryFlag.UNKNOWN;
    }

    @Override
    public float getBatteryPercentage() {
        return -1;
    }

    @Nullable
    @Override
    public Duration getBatteryLifeTime() {
        return null;
    }

    @Nullable
    @Override
    public Duration getBatteryFullLifeTime() {
        return null;
    }
}
