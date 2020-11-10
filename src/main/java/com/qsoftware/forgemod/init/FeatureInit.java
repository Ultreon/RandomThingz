package com.qsoftware.forgemod.init;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;

/**
 * Features initialization class.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
public class FeatureInit {
    public static final ConfiguredFeature<? extends IFeatureConfig, ?> ORE_RUBY = register("ore_ruby", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.RUBY_BLOCK.get().getDefaultState(), 8)).range(16).square());

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
