package com.ultreon.randomthingz.api;

import com.ultreon.randomthingz.item.MachineUpgradeItem;
import com.ultreon.randomthingz.item.upgrade.MachineUpgrade;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * A machine upgrade item, which goes into the upgrade slots of machines. Also see {@link
 * MachineUpgradeItem} and {@link
 * MachineUpgrade}.
 */
public interface IMachineUpgrade extends IForgeRegistryEntry<MachineUpgrade> {
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
