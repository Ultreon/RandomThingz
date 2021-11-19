package com.ultreon.randomthingz.world.gen.ores;

import com.ultreon.randomthingz.item.common.ItemMaterial;
import com.ultreon.randomthingz.world.gen.ores.configs.ChancedOreConfig;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
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
        if (config.getVeinSize() < 2) {
            return new ConfiguredFeatureQFM<>(Feature.EMERALD_ORE, new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), this.asBlockState()))
                    .setChance(1f / (float)config.getChance())
                    .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, bottom, config.getMaxHeight())))
                    .square()
                    .count(config.getVeinCount());
        }
        return new ConfiguredFeatureQFM<>(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, this.asBlockState(), config.getVeinSize()))
                .setChance(1f / (float)config.getChance())
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, bottom, config.getMaxHeight())))
                .square()
                .count(config.getVeinCount());
    }
}
