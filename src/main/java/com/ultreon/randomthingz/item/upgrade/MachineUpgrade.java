package com.ultreon.randomthingz.item.upgrade;

import com.ultreon.modlib.api.holders.ItemHolder;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.api.IMachineUpgrade;
import com.ultreon.randomthingz.common.interfaces.Named;
import com.ultreon.randomthingz.common.interfaces.Translatable;
import com.ultreon.randomthingz.init.ModMachineUpgrades;
import com.ultreon.randomthingz.item.MachineUpgradeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class MachineUpgrade extends ForgeRegistryEntry<MachineUpgrade> implements ItemHolder, IMachineUpgrade, Named, Translatable {
//    public static final MachineUpgrade PROCESSING_SPEED = new MachineUpgrade("processing_speed", Constants.UPGRADE_PROCESSING_SPEED_AMOUNT, .5f);
//    public static final MachineUpgrade OUTPUT_CHANCE = new MachineUpgrade("output_chance", Constants.UPGRADE_SECONDARY_OUTPUT_AMOUNT, .25f);
//    public static final MachineUpgrade ENERGY_CAPACITY = new MachineUpgrade("energy_capacity", 0, 0f, false);
//    public static final MachineUpgrade ENERGY_EFFICIENCY = new MachineUpgrade("energy_efficiency", Constants.UPGRADE_ENERGY_EFFICIENCY_AMOUNT, Constants.UPGRADE_ENERGY_EFFICIENCY_AMOUNT);
//    public static final MachineUpgrade RANGE = new MachineUpgrade("range", Constants.UPGRADE_RANGE_AMOUNT, .15f, false);

    private final float upgradeValue;
    private final float energyUsage;
    private final boolean displayValueAsPercentage;
    private Supplier<MachineUpgradeItem> item = () -> null;

    public MachineUpgrade(float upgradeValue, float energyUsage) {
        this(upgradeValue, energyUsage, true);
    }

    public MachineUpgrade(float upgradeValue, float energyUsage, boolean displayValueAsPercentage) {
        this.upgradeValue = upgradeValue;
        this.energyUsage = energyUsage;
        this.displayValueAsPercentage = displayValueAsPercentage;
    }

    public static void register() {
        for (MachineUpgrade value : ModMachineUpgrades.registry().getValues()) {
            MachineUpgradeItem item = new MachineUpgradeItem(value);
            item.setRegistryName(value.getSubRegistryName());
            ForgeRegistries.ITEMS.register(item);
            value.item = () -> item;
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

    @Override
    public ResourceLocation getSubRegistryName() {
        ResourceLocation registryName = getRegistryName();
        return new ResourceLocation(registryName.getNamespace(), registryName + "_upgrade");
    }

    @NotNull
    @Override
    public Item asItem() {
        MachineUpgradeItem item = this.item.get();
        if (item == null) throw new IllegalStateException("Machine upgrade item not registered yet: " + getSubRegistryName());
        return item;
    }

    @Override
    public String getTranslationId() {
        return "upgrade." + RandomThingz.MOD_ID + "." + getSubRegistryName();
    }
}
