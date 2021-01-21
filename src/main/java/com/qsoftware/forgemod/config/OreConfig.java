package com.qsoftware.forgemod.config;

import com.qsoftware.forgemod.modules.ores.Ore;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@FieldsAreNonnullByDefault
public class OreConfig {
    private final ForgeConfigSpec.BooleanValue masterSwitch;
    private final ForgeConfigSpec.IntValue veinCount;
    private final ForgeConfigSpec.IntValue veinSize;
    private final ForgeConfigSpec.IntValue minHeight;
    private final ForgeConfigSpec.IntValue maxHeight;

    public OreConfig(Ore ore, ForgeConfigSpec.Builder builder, ForgeConfigSpec.BooleanValue masterSwitch) {
        this.masterSwitch = masterSwitch;
        this.veinCount = builder
                .comment("Number of veins per chunk")
                .defineInRange(ore.getName() + ".veinCount", ore.getDefaultOreConfigs().getVeinCount(), 0, Integer.MAX_VALUE);
        this.veinSize = builder
                .comment("Size of veins")
                .defineInRange(ore.getName() + ".veinSize", ore.getDefaultOreConfigs().getVeinSize(), 0, 100);
        this.minHeight = builder
                .comment("Minimum Y-coordinate (base height) of veins")
                .defineInRange(ore.getName() + ".minHeight", ore.getDefaultOreConfigs().getMinHeight(), 0, 255);
        this.maxHeight = builder
                .comment("Maximum Y-coordinate (highest level) of veins")
                .defineInRange(ore.getName() + ".maxHeight", ore.getDefaultOreConfigs().getMaxHeight(), 0, 255);
    }

    public boolean isEnabled() {
        return masterSwitch.get() && getVeinCount() > 0 && getVeinSize() > 0;
    }

    public int getVeinCount() {
        return veinCount.get();
    }

    public int getVeinSize() {
        return veinSize.get();
    }

    public int getMinHeight() {
        return minHeight.get();
    }

    public int getMaxHeight() {
        return maxHeight.get();
    }
}
