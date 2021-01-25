package com.qsoftware.forgemod.util;

import org.jetbrains.annotations.Nullable;
import oshi.SystemInfo;

public enum VMType {
    VMWARE("VMware, Inc.", "VMware");

    private final String manufacturer;
    private final String name;

    VMType(String manufacturer, String name) {
        this.manufacturer = manufacturer;
        this.name = name;
    }

    @Nullable
    public static VMType getFromManufacturer(String manufacturer) {
        for (VMType vmType : values()) {
            if (vmType.manufacturer.equals(manufacturer)) {
                return vmType;
            }
        }
        return null;
    }

    @Nullable
    public static VMType getFromSystemInfo(SystemInfo systemInfo) {
        return getFromManufacturer(systemInfo.getHardware().getComputerSystem().getManufacturer());
    }

    public static boolean isGuestVM() {
        return getFromSystem() != null;
    }

    public static VMType getFromSystem() {
        return getFromSystemInfo(new SystemInfo());
    }

    public String getName() {
        return this.name;
    }
}
