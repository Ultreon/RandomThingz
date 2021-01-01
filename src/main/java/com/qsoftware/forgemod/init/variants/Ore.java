package com.qsoftware.forgemod.init.variants;

import com.qsoftware.forgemod.config.Config;
import com.qsoftware.forgemod.config.OreConfig;
import com.qsoftware.modlib.silentlib.block.IBlockProvider;
import com.qsoftware.modlib.silentutils.Lazy;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Handles ore blocks and default ore configs
 */
public enum Ore implements IBlockProvider {
    COPPER(() -> OreMaterial.COPPER, 3, 1, new DefaultOreConfigs(8, 8, 40, 90)),
    TIN(() -> OreMaterial.TIN, 3, 1, new DefaultOreConfigs(8, 8, 20, 80)),
    SILVER(() -> OreMaterial.SILVER, 4, 2, new DefaultOreConfigs(4, 8, 0, 40)),
    LEAD(() -> OreMaterial.LEAD, 4, 2, new DefaultOreConfigs(4, 8, 0, 30)),
    NICKEL(() -> OreMaterial.NICKEL, 5, 2, new DefaultOreConfigs(1, 6, 0, 24)),
    PLATINUM(() -> OreMaterial.PLATINUM, 5, 2, new DefaultOreConfigs(1, 8, 5, 20)),
    ZINC(() -> OreMaterial.ZINC, 3, 1, new DefaultOreConfigs(4, 8, 20, 60)),
    BISMUTH(() -> OreMaterial.BISMUTH, 3, 1, new DefaultOreConfigs(4, 8, 16, 64)),
    BAUXITE(() -> OreMaterial.ALUMINUM, 4, 1, new DefaultOreConfigs(6, 8, 15, 50)),
    URANIUM(() -> OreMaterial.URANIUM, 6, 2, new DefaultOreConfigs(1, 4, 0, 18)),
    ;

    private final Supplier<OreMaterial> metal;
    private final DefaultOreConfigs defaultOreConfigs;
    private final int hardness;
    private final int harvestLevel;
    private final Lazy<ConfiguredFeature<?, ?>> configuredFeature = Lazy.of(() -> {
        @SuppressWarnings("OptionalGetWithoutIsPresent") OreConfig config = this.getConfig().get();
        int bottom = config.getMinHeight();
        return Feature.ORE
                .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, this.asBlockState(), config.getVeinSize()))
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, bottom, config.getMaxHeight())))
                .square()
                .func_242731_b(config.getVeinCount());
    });

    Ore(Supplier<OreMaterial> metal, int hardness, int harvestLevel, DefaultOreConfigs defaultOreConfigs) {
        this.metal = metal;
        this.defaultOreConfigs = defaultOreConfigs;
        this.hardness = hardness;
        this.harvestLevel = harvestLevel;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public int getHardness() {
        return hardness;
    }

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public DefaultOreConfigs getDefaultOreConfigs() {
        return defaultOreConfigs;
    }

    public Optional<OreConfig> getConfig() {
        return Config.getOreConfig(this);
    }

    public ConfiguredFeature<?, ?> getConfiguredFeature() {
        return configuredFeature.get();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Block asBlock() {
        return metal.get().getOre().get();
    }

    public static class DefaultOreConfigs {
        private final int veinCount;
        private final int veinSize;
        private final int minHeight;
        private final int maxHeight;

        public DefaultOreConfigs(int veinCount, int veinSize, int minHeight, int maxHeight) {
            this.veinCount = veinCount;
            this.veinSize = veinSize;
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
        }

        public int getVeinCount() {
            return veinCount;
        }

        public int getVeinSize() {
            return veinSize;
        }

        public int getMinHeight() {
            return minHeight;
        }

        public int getMaxHeight() {
            return maxHeight;
        }
    }
}
