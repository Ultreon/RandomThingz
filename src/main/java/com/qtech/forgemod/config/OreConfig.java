package com.qtech.forgemod.config;

import com.qtech.forgemod.world.gen.ores.DefaultOre;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings({"unused", "NotNullFieldNotInitialized"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@FieldsAreNonnullByDefault
@Deprecated
public class OreConfig {
    @Deprecated
    private final ForgeConfigSpec.BooleanValue masterSwitch;
    @Deprecated
    private ForgeConfigSpec.IntValue veinCount;
    @Deprecated
    private ForgeConfigSpec.IntValue veinSize;
    @Deprecated
    private ForgeConfigSpec.IntValue minHeight;
    @Deprecated
    private ForgeConfigSpec.IntValue maxHeight;

    @Deprecated
    public OreConfig(DefaultOre ore, ForgeConfigSpec.Builder builder, ForgeConfigSpec.BooleanValue masterSwitch) {
        this.masterSwitch = masterSwitch;
//        this.veinCount = builder
//                .comment("Number of veins per chunk")
//                .defineInRange(ore.getName() + ".veinCount", ore.getOreConfig().getVeinCount(), 0, Integer.MAX_VALUE);
//        this.veinSize = builder
//                .comment("Size of veins")
//                .defineInRange(ore.getName() + ".veinSize", ore.getOreConfig().getVeinSize(), 0, 100);
//        this.minHeight = builder
//                .comment("Minimum Y-coordinate (base height) of veins")
//                .defineInRange(ore.getName() + ".minHeight", ore.getOreConfig().getMinHeight(), 0, 255);
//        this.maxHeight = builder
//                .comment("Maximum Y-coordinate (highest level) of veins")
//                .defineInRange(ore.getName() + ".maxHeight", ore.getOreConfig().getMaxHeight(), 0, 255);
    }

    @Deprecated
    public boolean isEnabled() {
        return masterSwitch.get() && getVeinCount() > 0 && getVeinSize() > 0;
    }

    @Deprecated
    public int getVeinCount() {
        return 0;
    }

    @Deprecated
    public int getVeinSize() {
        return 0;
    }

    @Deprecated
    public int getMinHeight() {
        return 0;
    }

    @Deprecated
    public int getMaxHeight() {
        return 0;
    }
}
