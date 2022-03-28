package com.ultreon.randomthingz.world.gen.features.common;

import com.google.common.collect.ImmutableSet;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.init.ModBlocks;
import com.ultreon.randomthingz.block.fluid.common.ModFluids;
import lombok.experimental.UtilityClass;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.OptionalInt;

/**
 * Features initialization class.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
@UtilityClass
public final class ModFeatures {
    // Trees
    public static final ConfiguredFeature<TreeConfiguration, ?> EUCALYPTUS_TREE = registerConfigured("eucalyptus_tree", Feature.TREE.configured(
                    new TreeConfiguration.TreeConfigurationBuilder(
                            SimpleStateProvider.simple(ModBlocks.EUCALYPTUS_LOG.asBlockState()),
                            new FancyTrunkPlacer(4, 7, 0),
                            SimpleStateProvider.simple(ModBlocks.EUCALYPTUS_LEAVES.asBlockState()),
                            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
                    ).ignoreVines().build()
            ));
    public static final ConfiguredFeature<TreeConfiguration, ?> CHERRY_TREE =
            registerConfigured("cherry_tree", Feature.TREE.configured(
                    new TreeConfiguration.TreeConfigurationBuilder(
                            SimpleStateProvider.simple(ModBlocks.CHERRY_LOG.asBlockState()),
                            new StraightTrunkPlacer(4, 2, 0),
                            SimpleStateProvider.simple(ModBlocks.CHERRY_LEAVES.asBlockState()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 3),
                            new TwoLayersFeatureSize(0, 0, 0)
                    ).ignoreVines().build()
            ));
//    Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(SimpleStateProvider.simple(ModBlocks.CHERRY_LOG.asBlockState()), SimpleStateProvider.simple(ModBlocks.CHERRY_LEAVES.asBlockState()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 3), new StraightTrunkPlacer(4, 2, 0), )).ignoreVines().build()));

    // Lakes
    public static final ConfiguredFeature<LakeFeature.Configuration, ?> OIL_LAKE = registerConfigured("lake_oil", Feature.LAKE.configured(
                    new LakeFeature.Configuration(
                            SimpleStateProvider.simple(ModFluids.OIL.defaultFluidState().createLegacyBlock()),
                            SimpleStateProvider.simple(Blocks.DIRT)
                    )
            ));

    // Lakes
    public static final ConfiguredFeature<SpringConfiguration, ?> OIL_SPRING = registerConfigured("lake_oil", Feature.SPRING.configured(
                    new SpringConfiguration(ModFluids.OIL.defaultFluidState(), true, 5, 4, ImmutableSet.of(Blocks.DIRT)))
            );

    private static <F extends Feature<FC>, FC extends FeatureConfiguration> ConfiguredFeature<FC, F> registerConfigured(String key, ConfiguredFeature<FC, F> configured) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, RandomThingz.res(key), configured);
    }

    private static PlacedFeature register(String key, PlacedFeature configuredFeature) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, RandomThingz.res(key), configuredFeature);
    }
}
