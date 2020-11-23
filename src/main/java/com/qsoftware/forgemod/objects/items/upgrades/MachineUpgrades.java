package com.qsoftware.forgemod.objects.items.upgrades;

import com.qsoftware.forgemod.api.IMachineUpgrade;
import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.util.Constants;
import com.qsoftware.silent.lib.registry.ItemRegistryObject;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;

import java.util.Locale;

public enum MachineUpgrades implements IItemProvider, IMachineUpgrade {
    PROCESSING_SPEED(Constants.UPGRADE_PROCESSING_SPEED_AMOUNT, 0.5f),
    OUTPUT_CHANCE(Constants.UPGRADE_SECONDARY_OUTPUT_AMOUNT, 0.25f),
    ENERGY_CAPACITY(0, 0.0f, false),
    ENERGY_EFFICIENCY(Constants.UPGRADE_ENERGY_EFFICIENCY_AMOUNT, Constants.UPGRADE_ENERGY_EFFICIENCY_AMOUNT),
    RANGE(Constants.UPGRADE_RANGE_AMOUNT, 0.15f, false);

    private final float upgradeValue;
    private final float energyUsage;
    private final boolean displayValueAsPercentage;
    @SuppressWarnings("NonFinalFieldInEnum")
    private ItemRegistryObject<MachineUpgradeItem> item;

    MachineUpgrades(float upgradeValue, float energyUsage) {
        this(upgradeValue, energyUsage, true);
    }

    MachineUpgrades(float upgradeValue, float energyUsage, boolean displayValueAsPercentage) {
        this.upgradeValue = upgradeValue;
        this.energyUsage = energyUsage;
        this.displayValueAsPercentage = displayValueAsPercentage;
    }

    public static void register() {
        for (MachineUpgrades value : values()) {
            value.item = new ItemRegistryObject<>(Registration.ITEMS.register(value.getName(), () ->
                    new MachineUpgradeItem(value)));
        }
    }

    @Override
    public float getEnergyUsageMultiplier() {
        return energyUsage;
    }

    @Override
    public float getUpgradeValue() {
        return upgradeValue;
    }

    @Override
    public boolean displayValueAsPercentage() {
        return displayValueAsPercentage;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT) + "_upgrade";
    }

    @Override
    public Item asItem() {
        return item.get();
    }
}
