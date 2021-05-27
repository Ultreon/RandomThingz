package com.qtech.randomthingz.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
//import oshi.SystemInfo;

@RequiredArgsConstructor
public enum VMType {
    VMWARE("VMware, Inc.", "VMware");

    @Getter
    private final String manufacturer;
    @Getter
    private final String name;

    @Nullable
    public static VMType getFromManufacturer(String manufacturer) {
        for (VMType vmType : values()) {
            if (vmType.manufacturer.equals(manufacturer)) {
                return vmType;
            }
        }
        return null;
    }

//    @Nullable
//    public static VMType getFromSystemInfo(SystemInfo systemInfo) {
//        return getFromManufacturer(systemInfo.getHardware().getComputerSystem().getManufacturer());
//    }

//    public static boolean isGuestVM() {
//        return getFromSystem() != null;
//    }

//    public static VMType getFromSystem() {
//        return getFromSystemInfo(new SystemInfo());
//    }
}
