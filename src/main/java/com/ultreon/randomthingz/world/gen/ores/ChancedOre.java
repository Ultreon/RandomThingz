package com.ultreon.randomthingz.world.gen.ores;

import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.world.gen.ores.configs.ChancedOreConfig;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChancedOre extends DefaultOre {
    private final ChancedOreConfig config;

    public ChancedOre(String name, Supplier<ItemMaterial> material, int hardness, int harvestLevel, ChancedOreConfig config) {
        super(name, material, hardness, harvestLevel, config);
        this.config = config;
    }

    public ChancedOre(String name, Supplier<ItemMaterial> material, int hardness, int harvestLevel, ChancedOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        super(name, material, hardness, harvestLevel, config, predicate);
        this.config = config;
    }

    public ChancedOre(String name, Supplier<ItemMaterial> material, int hardness, int resistance, int harvestLevel, ChancedOreConfig config) {
        super(name, material, hardness, resistance, harvestLevel, config);
        this.config = config;
    }

    public ChancedOre(String name, Supplier<ItemMaterial> material, int hardness, int resistance, int harvestLevel, ChancedOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        super(name, material, hardness, resistance, harvestLevel, config, predicate);
        this.config = config;
    }

    @Override
    public ConfiguredFeature<?, ?> generate() {
        int bottom = config.getMinHeight();
        if (config.getVeinSize() <= 2) {
            return new ConfiguredFeatureQFM<>(Feature.EMERALD_ORE, new ReplaceBlockConfiguration(Blocks.STONE.defaultBlockState(), this.asBlockState()))
                    .setChance(1f / (float) config.getChance())
                    .decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(bottom, bottom, config.getMaxHeight())))
                    .squared()
                    .count(config.getVeinCount());
        }
        return new ConfiguredFeatureQFM<>(Feature.ORE, new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, this.asBlockState(), config.getVeinSize()))
                .setChance(1f / (float) config.getChance())
                .decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(bottom, bottom, config.getMaxHeight())))
                .squared()
                .count(config.getVeinCount());
    }
}
