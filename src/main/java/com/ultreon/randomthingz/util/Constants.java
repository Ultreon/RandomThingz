package com.ultreon.randomthingz.util;

import com.ultreon.randomthingz.RandomThingz;
import lombok.experimental.UtilityClass;
import net.minecraft.resources.ResourceLocation;

@UtilityClass
public final class Constants {
    // Recipe resource locations.
    public static final ResourceLocation ALLOY_SMELTING = RandomThingz.rl("alloy_smelting");
    public static final ResourceLocation ARCANE_ESCALATING = RandomThingz.rl("arcane_escalating");
    public static final ResourceLocation ENCHANTING = RandomThingz.rl("enchanting");
    public static final ResourceLocation COMPRESSING = RandomThingz.rl("compressing");
    public static final ResourceLocation CRUSHING = RandomThingz.rl("crushing");
    public static final ResourceLocation DRYING = RandomThingz.rl("drying");
    public static final ResourceLocation INFUSING = RandomThingz.rl("infusing");
    public static final ResourceLocation MIXING = RandomThingz.rl("mixing");
    public static final ResourceLocation REFINING = RandomThingz.rl("refining");
    public static final ResourceLocation SOLIDIFYING = RandomThingz.rl("solidifying");

    // Machine upgrades
    public static final int UPGRADES_PER_SLOT = 1;
    public static final float UPGRADE_PROCESSING_SPEED_AMOUNT = 0.5f;
    public static final float UPGRADE_SECONDARY_OUTPUT_AMOUNT = 0.1f;
    public static final float UPGRADE_ENERGY_EFFICIENCY_AMOUNT = -0.15f;
    public static final int UPGRADE_RANGE_AMOUNT = 2;
}
