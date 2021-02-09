package com.qsoftware.forgemod.util;

import com.qsoftware.forgemod.QForgeMod;
import lombok.experimental.UtilityClass;
import net.minecraft.util.ResourceLocation;

@UtilityClass
public final class Constants {
    // Recipe resource locations.
    public static final ResourceLocation ALLOY_SMELTING = QForgeMod.rl("alloy_smelting");
    public static final ResourceLocation ENCHANTING = QForgeMod.rl("enchanting");
    public static final ResourceLocation COMPRESSING = QForgeMod.rl("compressing");
    public static final ResourceLocation CRUSHING = QForgeMod.rl("crushing");
    public static final ResourceLocation DRYING = QForgeMod.rl("drying");
    public static final ResourceLocation INFUSING = QForgeMod.rl("infusing");
    public static final ResourceLocation MIXING = QForgeMod.rl("mixing");
    public static final ResourceLocation REFINING = QForgeMod.rl("refining");
    public static final ResourceLocation SOLIDIFYING = QForgeMod.rl("solidifying");

    // Machine upgrades
    public static final int UPGRADES_PER_SLOT = 1;
    public static final float UPGRADE_PROCESSING_SPEED_AMOUNT = 0.5f;
    public static final float UPGRADE_SECONDARY_OUTPUT_AMOUNT = 0.1f;
    public static final float UPGRADE_ENERGY_EFFICIENCY_AMOUNT = -0.15f;
    public static final int UPGRADE_RANGE_AMOUNT = 2;
}
