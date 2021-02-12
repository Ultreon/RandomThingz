package com.qsoftware.forgemod.modules.environment;

import com.qsoftware.forgemod.modules.blocks.ModFluids;
import com.qsoftware.forgemod.modules.blocks.ModBlocks;
import com.qsoftware.forgemod.util.ExceptionUtil;
import lombok.experimental.UtilityClass;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

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
    public static final ConfiguredFeature<?, ?> ORE_RUBY = register("ore_ruby", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.RUBY_BLOCK.asBlockState(), 8)).range(16).square());

    // Trees
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> EUCALYPTUS_TREE = register("eucalyptus_tree", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.EUCALYPTUS_LOG.asBlockState()), new SimpleBlockStateProvider(ModBlocks.EUCALYPTUS_LEAVES.asBlockState()), new FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4), new FancyTrunkPlacer(4, 7, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE = register("cherry_tree", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.CHERRY_LOG.asBlockState()), new SimpleBlockStateProvider(ModBlocks.CHERRY_LEAVES.asBlockState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(0, 0, 0))).setIgnoreVines().build()));

    // Lakes
    public static final ConfiguredFeature<?, ?> LAKE_OIL = register("lake_oil", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(ModFluids.OIL.getDefaultState().getBlockState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(60))));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
