package com.ultreon.randomthingz.core;

import com.ultreon.randomthingz.core.natives.Kernel32;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;

class SystemBatteryWin32 implements SystemBattery {
    private final Kernel32 kernel32 = Kernel32.INSTANCE;
    private final Kernel32.SYSTEM_POWER_STATUS systemPowerStatus = new Kernel32.SYSTEM_POWER_STATUS();

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public ACLineStatus getACLineStatus() {
        update();
        return systemPowerStatus.getACLineStatus();
    }

    private void update() {
        this.kernel32.GetSystemPowerStatus(this.systemPowerStatus);
    }

    @Override
    public BatteryFlag getBatteryFlag() {
        update();
        return systemPowerStatus.getBatteryFlag();
    }

    @Override
    public float getBatteryPercentage() {
        update();
        return systemPowerStatus.getBatteryLifePercent();
    }

    @Override
    public @Nullable Duration getBatteryLifeTime() {
        update();
        int batteryLifeTime = systemPowerStatus.getBatteryLifeTime();
        return batteryLifeTime == -1 ? null : Duration.ofSeconds(batteryLifeTime);
    }

    @Override
    public @Nullable Duration getBatteryFullLifeTime() {
        update();
        int batteryLifeTime = systemPowerStatus.getBatteryFullLifeTime();
        return batteryLifeTime == -1 ? null : Duration.ofSeconds(batteryLifeTime);
    }
}
