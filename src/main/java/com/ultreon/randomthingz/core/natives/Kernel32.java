package com.ultreon.randomthingz.core.natives;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;
import com.ultreon.randomthingz.core.SystemBattery;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public interface Kernel32 extends StdCallLibrary {
    Kernel32 INSTANCE = Native.load("Kernel32", Kernel32.class);

    /**
     * @see <a href="https://docs.microsoft.com/en-us/windows/win32/api/winbase/ns-winbase-system_power_status">Microsoft documentation.</a>
     */
    @SuppressWarnings("unused")
    class SYSTEM_POWER_STATUS extends Structure {
        public byte ACLineStatus;
        public byte BatteryFlag;
        public byte BatteryLifePercent;
        public byte Reserved1;
        public int BatteryLifeTime;
        public int BatteryFullLifeTime;

        @Override
        protected List<String> getFieldOrder() {
            ArrayList<String> fields = new ArrayList<>();
            fields.add("ACLineStatus");
            fields.add("BatteryFlag");
            fields.add("BatteryLifePercent");
            fields.add("Reserved1");
            fields.add("BatteryLifeTime");
            fields.add("BatteryFullLifeTime");
            return fields;
        }

        /**
         * The AC power status
         * @return ac line status
         */
        public SystemBattery.ACLineStatus getACLineStatus() {
            return switch (ACLineStatus) {
                case (0) -> SystemBattery.ACLineStatus.OFFLINE;
                case (1) -> SystemBattery.ACLineStatus.ONLINE;
                default -> SystemBattery.ACLineStatus.UNKNOWN;
            };
        }

        /**
         * The battery charge status
         * @return battery flag
         */
        public SystemBattery.BatteryFlag getBatteryFlag() {
            return switch (BatteryFlag) {
                case (1) -> SystemBattery.BatteryFlag.HIGH; // "High, more than 66 percent";
                case (2) -> SystemBattery.BatteryFlag.LOW; // "Low, less than 33 percent";
                case (4) -> SystemBattery.BatteryFlag.CRITICAL; // "Critical, less than five percent";
                case (8) -> SystemBattery.BatteryFlag.CHARGING; // "Charging";
                case ((byte) 128) -> SystemBattery.BatteryFlag.NO_BATTERY; // "No system battery";
                default -> SystemBattery.BatteryFlag.UNKNOWN; // "Unknown";
            };
        }

        /**
         * The percentage of full battery charge remaining
         * @return Percentage (between 0 and 100) of battery charge remaining. -1 is unknown.
         */
        public int getBatteryLifePercent() {
            return (BatteryLifePercent == (byte) 255) ? -1 : BatteryLifePercent;
        }

        /**
         * The number of seconds of battery life remaining.
         *
         * @return seconds of battery life remaining. -1 is unknown.
         */
        public int getBatteryLifeTime() {
            return (BatteryLifeTime == -1) ? -1 : BatteryLifeTime;
        }

        /**
         * The number of seconds of battery life when at full charge
         *
         * @return seconds of battery life when at full charge. -1 is unknown
         */
        public int getBatteryFullLifeTime() {
            return (BatteryFullLifeTime == -1) ? -1 : BatteryFullLifeTime;
        }

        @Override
        public String toString() {
            return "ACLineStatus: " + getACLineStatus() + "\n" +
                    "Battery Flag: " + getBatteryFlag() + "\n" +
                    "Battery Life: " + getBatteryLifePercent() + "\n" +
                    "Battery Left: " + getBatteryLifeTime() + "\n" +
                    "Battery Full: " + getBatteryFullLifeTime() + "\n";
        }
    }

    /**
     * Fill the structure.
     */
    int GetSystemPowerStatus(SYSTEM_POWER_STATUS result);
}