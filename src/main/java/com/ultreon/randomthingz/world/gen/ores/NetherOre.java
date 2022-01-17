package com.ultreon.randomthingz.world.gen.ores;

import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.world.gen.ores.configs.DefaultOreConfig;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
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
@FieldsAreNonnullByDefault
public class NetherOre extends DefaultOre {
    public NetherOre(String name, Supplier<ItemMaterial> material, int hardness, int harvestLevel, DefaultOreConfig config) {
        super(name, material, hardness, harvestLevel, config);
    }

    public NetherOre(String name, Supplier<ItemMaterial> material, int hardness, int harvestLevel, DefaultOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        super(name, material, hardness, harvestLevel, config, predicate);
    }

    @Override
    public ConfiguredFeature<?, ?> generate() {
        DefaultOreConfig config = this.config;
        int bottom = config.getMinHeight();
        if (config.getVeinSize() <= 2) {
            return Feature.EMERALD_ORE
                    .configured(new ReplaceBlockConfiguration(Blocks.NETHERRACK.defaultBlockState(), this.asBlockState()))
                    .decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(bottom, bottom, config.getMaxHeight())))
                    .squared()
                    .count(config.getVeinCount());
        }
        return Feature.ORE
                .configured(new OreConfiguration(OreConfiguration.Predicates.NETHERRACK, this.asBlockState(), config.getVeinSize()))
                .decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(bottom, bottom, config.getMaxHeight())))
                .squared()
                .count(config.getVeinCount());
    }
}
