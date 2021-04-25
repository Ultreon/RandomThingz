package com.qtech.forgemod.api;

import com.qtech.forgemod.modules.items.objects.upgrades.MachineUpgradeItem;
import com.qtech.forgemod.modules.items.objects.upgrades.MachineUpgrades;

/**
 * A machine upgrade item, which goes into the upgrade slots of machines. Also see {@link
 * MachineUpgradeItem} and {@link
 * MachineUpgrades}.
 */
public interface IMachineUpgrade {
    /**
     * Added to machine energy consumption as a multiplier. Positive numbers will increase energy
     * usage, negative numbers will decrease it instead.
     *
     * @return Energy usage multiplier
     */
    float getEnergyUsageMultiplier();

    /**
     * Gets a "value" associated with the upgrade, mostly intended for use with item tooltips.
     *
     * @return A value to display.
     */
    float getUpgradeValue();

    /**
     * Determine if the value should be displayed as a percentage, mostly intended for use with item
     * tooltips.
     *
     * @return True to display as a percentage, false to display as-is.
     */
    boolean displayValueAsPercentage();

    default int getDisplayValue() {
        float value = getUpgradeValue();
        return displayValueAsPercentage() ? (int) (value * 100) : (int) value;
    }
}
