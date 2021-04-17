package com.qsoftware.forgemod.modules.environment.ores;

import com.qsoftware.forgemod.modules.environment.ores.configs.ChancedOreConfig;
import com.qsoftware.forgemod.modules.items.OreMaterial;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
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
    private final ChancedOreConfig config;

    public ChancedNetherOre(String name, Supplier<OreMaterial> material, int hardness, int harvestLevel, ChancedOreConfig config) {
        super(name, material, hardness, harvestLevel, config);
        this.config = config;
    }

    public ChancedNetherOre(String name, Supplier<OreMaterial> material, int hardness, int harvestLevel, ChancedOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        super(name, material, hardness, harvestLevel, config, predicate);
        this.config = config;
    }

    @Override
    public ConfiguredFeature<?, ?> generate() {
        int bottom = config.getMinHeight();
        if (config.getVeinSize() < 2) {
            return new ConfiguredFeatureQFM<>(Feature.EMERALD_ORE, new ReplaceBlockConfig(Blocks.NETHERRACK.getDefaultState(), this.asBlockState()))
                    .setChance(1f / (float)config.getChance())
                    .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, bottom, config.getMaxHeight())))
                    .square()
                    .count(config.getVeinCount());
        }
        return Feature.ORE
                .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, this.asBlockState(), config.getVeinSize()))
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, bottom, config.getMaxHeight())))
                .withPlacement(Placement.CHANCE.configure(new ChanceConfig(config.getChance())))
                .square()
                .count(config.getVeinCount());
    }
}
