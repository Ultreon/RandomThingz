package com.ultreon.randomthingz.world.gen.features.common;

import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.fluid.common.ModFluids;
import lombok.experimental.UtilityClass;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.UniformInt;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

import java.util.OptionalInt;

/**
 * Features initialization class.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
@UtilityClass
public final class ModFeatures {
    // Ore
//    public static final ConfiguredFeature<?, ?> ORE_RUBY = register("ore_ruby", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.RUBY_BLOCK.asBlockState(), 8)).range(16).square());

    // Trees
    public static final ConfiguredFeature<TreeConfiguration, ?> EUCALYPTUS_TREE = register("eucalyptus_tree", Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(ModBlocks.EUCALYPTUS_LOG.asBlockState()), new SimpleStateProvider(ModBlocks.EUCALYPTUS_LEAVES.asBlockState()), new FancyFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(4), 4), new FancyTrunkPlacer(4, 7, 0), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Types.MOTION_BLOCKING).build()));
    public static final ConfiguredFeature<TreeConfiguration, ?> CHERRY_TREE = register("cherry_tree", Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(ModBlocks.CHERRY_LOG.asBlockState()), new SimpleStateProvider(ModBlocks.CHERRY_LEAVES.asBlockState()), new BlobFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(4), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(0, 0, 0))).ignoreVines().build()));

    // Lakes
    public static final ConfiguredFeature<?, ?> LAKE_OIL = register("lake_oil", Feature.LAKE.configured(new BlockStateConfiguration(ModFluids.OIL.defaultFluidState().createLegacyBlock())).decorated(FeatureDecorator.WATER_LAKE.configured(new ChanceDecoratorConfiguration(60))));

    private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
