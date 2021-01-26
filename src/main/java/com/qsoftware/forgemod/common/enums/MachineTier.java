package com.qsoftware.forgemod.common.enums;

public enum MachineTier {
    BASIC(0, 10_000, 1.0f),
    STANDARD(2, 50_000, 2.0f),
    HEAVY(4, 150_000, 2.5f),
    SUPER(6, 450_000, 3.0f),
    EXTREME(7, 1_350_000, 3.0f),
    ULTRA(8, 4_050_000, 5.0f);

    private final int upgradeSlots;
    private final int energyCapacity;
    private final float processingSpeed;

    MachineTier(int upgradeSlots, int energyCapacity, float processingSpeed) {
        this.upgradeSlots = upgradeSlots;
        this.energyCapacity = energyCapacity;
        this.processingSpeed = processingSpeed;
    }

    public int getUpgradeSlots() {
        return upgradeSlots;
    }

    public int getEnergyCapacity() {
        return energyCapacity;
    }

    public float getProcessingSpeed() {
        return processingSpeed;
    }
}
