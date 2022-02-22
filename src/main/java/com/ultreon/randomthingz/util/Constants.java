package com.ultreon.randomthingz.util;

import com.ultreon.randomthingz.RandomThingz;
import lombok.experimental.UtilityClass;
import net.minecraft.resources.ResourceLocation;

@UtilityClass
public final class Constants {
    // Recipe resource locations.
    public static final ResourceLocation ALLOY_SMELTING = RandomThingz.res("alloy_smelting");
    public static final ResourceLocation ARCANE_ESCALATING = RandomThingz.res("arcane_escalating");
    public static final ResourceLocation ENCHANTING = RandomThingz.res("enchanting");
    public static final ResourceLocation COMPRESSING = RandomThingz.res("compressing");
    public static final ResourceLocation CRUSHING = RandomThingz.res("crushing");
    public static final ResourceLocation DRYING = RandomThingz.res("drying");
    public static final ResourceLocation INFUSING = RandomThingz.res("infusing");
    public static final ResourceLocation MIXING = RandomThingz.res("mixing");
    public static final ResourceLocation REFINING = RandomThingz.res("refining");
    public static final ResourceLocation SOLIDIFYING = RandomThingz.res("solidifying");

    // Machine upgrades
    public static final int UPGRADES_PER_SLOT = 1;
    public static final float UPGRADE_PROCESSING_SPEED_AMOUNT = .5f;
    public static final float UPGRADE_SECONDARY_OUTPUT_AMOUNT = .1f;
    public static final float UPGRADE_ENERGY_EFFICIENCY_AMOUNT = -.15f;
    public static final int UPGRADE_RANGE_AMOUNT = 2;
}
