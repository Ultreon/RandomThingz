package com.qsoftware.forgemod.modules.environment.ores;

import com.qsoftware.forgemod.modules.environment.ores.configs.ChancedOreConfigs;
import com.qsoftware.forgemod.modules.items.OreMaterial;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChancedNetherOre extends DefaultOre {
    private final ChancedOreConfigs config;

    public ChancedNetherOre(String name, Supplier<OreMaterial> material, int hardness, int harvestLevel, ChancedOreConfigs config) {
        super(name, material, hardness, harvestLevel, config);
        this.config = config;
    }

    public ChancedNetherOre(String name, Supplier<OreMaterial> material, int hardness, int harvestLevel, ChancedOreConfigs config, Predicate<BiomeLoadingEvent> predicate) {
        super(name, material, hardness, harvestLevel, config, predicate);
        this.config = config;
    }

    @Override
    public ConfiguredFeature<?, ?> generate() {
        int bottom = config.getMinHeight();
        return Feature.ORE
                .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, this.asBlockState(), config.getVeinSize()))
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, bottom, config.getMaxHeight())))
                .withPlacement(Placement.CHANCE.configure(new ChanceConfig(config.getChance())))
                .square()
                .func_242731_b(config.getVeinCount());
    }
}
