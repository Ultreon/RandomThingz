package com.ultreon.randomthingz.core;

import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;

@SuppressWarnings("unused")
public interface SystemBattery {
    boolean isAvailable();

    ACLineStatus getACLineStatus();
    BatteryFlag getBatteryFlag();
    float getBatteryPercentage();
    @Nullable Duration getBatteryLifeTime();
    @Nullable Duration getBatteryFullLifeTime();

    enum ACLineStatus {
        OFFLINE, ONLINE, UNKNOWN
    }

    enum BatteryFlag {
        HIGH, LOW, CRITICAL, CHARGING, NO_BATTERY, UNKNOWN
    }

    static SystemBattery get() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return new SystemBatteryWin32();
        } else {
            return new SystemBatteryDummy();
        }
    }
}
