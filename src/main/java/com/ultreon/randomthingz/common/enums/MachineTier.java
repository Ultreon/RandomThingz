package com.ultreon.randomthingz.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MachineTier {
    BASIC(0, 10_000, 1f),
    STANDARD(2, 50_000, 2f),
    HEAVY(4, 150_000, 2.5f),
    SUPER(6, 450_000, 3f),
    EXTREME(8, 1_350_000, 3f),
    ULTRA(10, 4_050_000, 5f);

    @Getter
    private final int upgradeSlots;
    @Getter
    private final int energyCapacity;
    @Getter
    private final float processingSpeed;
}
