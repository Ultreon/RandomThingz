package com.ultreon.randomthingz.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import oshi.SystemInfo;

import javax.annotation.Nonnull;
import java.util.Objects;

@RequiredArgsConstructor
public enum VmProvider {
    VMWARE("VMware, Inc.", "VMware"),
    UNKNOWN("", ""),
    NONE("", ""),
    ;

    @Getter
    private final String manufacturer;
    @Getter
    private final String name;

    @Nonnull
    public static @NotNull VmProvider getFromManufacturer(String manufacturer) {
        if (manufacturer == null || Objects.equals(manufacturer, "")) {
            return UNKNOWN;
        }

        for (VmProvider vmProvider : values()) {
            if (vmProvider.manufacturer.equals(manufacturer)) {
                return vmProvider;
            }
        }
        return NONE;
    }

    @Nonnull
    public static VmProvider get() {
        String manufacturer = null;
        try {
            manufacturer = new SystemInfo().getHardware().getComputerSystem().getFirmware().getManufacturer();
        } catch (Throwable ignored) {

        }

        if (manufacturer == null) {
            return UNKNOWN;
        } else {
            return getFromManufacturer(manufacturer);
        }
    }

    public static boolean isRunningInVM() {
        String manufacturer = null;
        try {
            manufacturer = new SystemInfo().getHardware().getComputerSystem().getFirmware().getManufacturer();
        } catch (Throwable ignored) {

        }
        if (manufacturer == null) {
            return false;
        } else {
            VmProvider fromManufacturer = getFromManufacturer(manufacturer);
            return fromManufacturer != UNKNOWN && fromManufacturer != NONE;
        }
    }
}
