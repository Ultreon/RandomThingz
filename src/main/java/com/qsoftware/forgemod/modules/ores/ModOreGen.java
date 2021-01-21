package com.qsoftware.forgemod.modules.ores;

import com.mojang.datafixers.util.Pair;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD;

/**
 * Ore generator class.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID)
public final class ModOreGen {
    private static final ConcurrentHashMap<ResourceLocation, ArrayList<HashMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>>>> oreMap = new ConcurrentHashMap<>();
    private static final ArrayList<Pair<Predicate<BiomeLoadingEvent>, ConfiguredFeature<?, ?>>> ores = new ArrayList<>();

    @SuppressWarnings("unused")
    public static class OreProps {
        private final ConfiguredFeature<?, ?> feature;

        public OreProps(ConfiguredFeature<OreFeatureConfig, ?> feature) {
            this.feature = feature;
        }

        public OreProps chance(int chance) {
            feature.chance(chance);
            return this;
        }

        public OreProps count(int count) {
            feature.func_242731_b(count);
            return this;
        }

        public OreProps countExtra(int count, int extraChance, int extraCount) {
            feature.withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(count, extraChance, extraCount)));
            return this;
        }

        public OreProps countMultilayer(int base) {
            feature.withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(base)));
            return this;
        }

        public OreProps square() {
            feature.square();
            return this;
        }

        public OreProps range(int top) {
            feature.range(top);
            return this;
        }

        public OreProps range(int top, int bottom) {
            feature.withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, 0, top)));
            return this;
        }

        public OreProps range(int top, int topOffset, int bottom) {
            feature.withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, topOffset, top)));
            return this;
        }

        public OreProps depthAverage(int baseline, int spread) {
            feature.withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(baseline, spread)));
            return this;
        }
    }

    /**
     * Create ore features.
     *
     * @see #onBiomeLoading(BiomeLoadingEvent)
     */
    public static void createOresFeatures() {
        // Started generating ores.
        QForgeMod.LOGGER.info("-===========- Create Ore Features [START] -===========-");

        addOreFeature((a) -> a.count(12).range(64, 48), 6,
                new BlockMatchRuleTest(Blocks.DIRT), ModBlocks.GILDED_DIRT.get(),
                (b) -> b.getCategory() == Biome.Category.RIVER
        );
        addOreFeature((a) -> a.count(6).range(25, 8), 6, BASE_STONE_OVERWORLD, ModBlocks.RUBY_ORE.get(),
                (b) -> b.getCategory() == Biome.Category.EXTREME_HILLS
        );
        addOreFeature((a) -> a.count(8).range(30, 8), 8, BASE_STONE_OVERWORLD, ModBlocks.AMETHYST_ORE.get(),
                (b) -> b.getCategory() == Biome.Category.EXTREME_HILLS
        );
        addOreFeature((a) -> a.count(8).range(30, 8), 8, BASE_STONE_OVERWORLD, ModBlocks.MALACHITE_ORE.get(),
                (b) -> b.getCategory() == Biome.Category.EXTREME_HILLS || b.getCategory() == Biome.Category.DESERT
        );
        addOreFeature((a) -> a.count(8).range(30, 8), 8, BASE_STONE_OVERWORLD, ModBlocks.PERIDOT_ORE.get(),
                (b) -> b.getCategory() == Biome.Category.EXTREME_HILLS || b.getCategory() == Biome.Category.DESERT
        );
        addOreFeature((a) -> a.count(4).range(15, 1), 2, BASE_STONE_OVERWORLD, ModBlocks.TANZANITE_ORE.get(),
                (b) -> b.getClimate().temperature >= 1.0f
        );
        addOreFeature((a) -> a.count(2).range(11, 8), 1, BASE_STONE_OVERWORLD, ModBlocks.ULTRINIUM_ORE.get(),
                (b) -> b.getClimate().temperature >= 1.0f
        );
        addOreFeature((a) -> a.chance(64).count(1).range(7, 8), 1, BASE_STONE_OVERWORLD, ModBlocks.INFINITY_ORE.get(),
                (b) -> b.getClimate().temperature >= 1.0f
        );

        // Ended generating ores.
        QForgeMod.LOGGER.info("-===========- Create Ore Features [ END ] -===========-");
    }

    public static void addOreFeature(Function<OreProps, OreProps> oreProps, int size, RuleTest filler, Block ore, Predicate<BiomeLoadingEvent> biome) {
        // Ore Feature
        BlockState blockState = ore.getDefaultState();
        OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(filler, blockState, size);

        // Configured Feature
        ConfiguredFeature<?, ?> configuredFeature = oreProps.apply(new OreProps(Feature.ORE.withConfiguration(oreFeatureConfig))).feature;

        // Add ore to list.
        ores.add(new Pair<>(biome, configuredFeature));
    }

    @SuppressWarnings("unused")
    @Deprecated
    protected static void createOreFeature(int count, int bottomOffset, int topOffset, int maximum, int size, RuleTest filler, Block ore, Biome[] biomes) {
        // Ore Feature
        BlockState blockState = ore.getDefaultState();
        OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(filler, blockState, size);

        // Configured Feature
        ConfiguredFeature<?, ?> configuredFeature = Feature.ORE.withConfiguration(oreFeatureConfig).range(maximum - topOffset).square().func_242731_b(count);

        // Add feature
        for (Biome biome : biomes) {
            QForgeMod.LOGGER.info("Adding Ore Mapping for: '" + ore.getRegistryName() + "' to biome: " + biome.getRegistryName());
            addOreMapping(biome.getRegistryName(), new HashMap.SimpleEntry<>(GenerationStage.Decoration.UNDERGROUND_ORES, configuredFeature));
        }
    }

    @SuppressWarnings("unused")
    @Deprecated
    protected static void createOreFeature(int count, int bottomOffset, int topOffset, int maximum, int size, RuleTest filler, Block ore, Biome biome) {
        // Ore Feature
        BlockState blockState = ore.getDefaultState();
        OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(filler, blockState, size);

        // Configured Feature
        ConfiguredFeature<?, ?> configuredFeature = Feature.ORE.withConfiguration(oreFeatureConfig).range(maximum - topOffset).square().func_242731_b(count);

        // Add feature
        QForgeMod.LOGGER.info("Adding Ore Mapping for: '" + ore.getRegistryName() + "' to biome: " + biome.getRegistryName());
        addOreMapping(biome.getRegistryName(), new HashMap.SimpleEntry<>(GenerationStage.Decoration.UNDERGROUND_ORES, configuredFeature));
    }

    @Deprecated
    private static void addOreMapping(ResourceLocation registryName, AbstractMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>> configEntry) {
        if (!oreMap.containsKey(registryName)) {
            oreMap.put(registryName, new ArrayList<>());
        }

        oreMap.get(registryName).add(configEntry);
    }

    /**
     * In this method ores will be added to specified biomes, the method is called when biomes are loading.
     *
     * @param event event for biome loading.
     */
    @SuppressWarnings("CommentedOutCode")
    @SubscribeEvent
    public void onBiomeLoading(BiomeLoadingEvent event) {
        int i = 0;
//        if (oreMap.containsKey(event.getName())) {
//            ArrayList<AbstractMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>>> list = oreMap.get(event.getName());
//            for (AbstractMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>> item : list) {
//                event.getGeneration().withFeature(item.getKey(), item.getValue());
//                i++;
//            }
//        }

        for (Pair<Predicate<BiomeLoadingEvent>, ConfiguredFeature<?, ?>> ore : ores) {
            if (ore.getFirst().test(event)) {
                event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore.getSecond());
                i++;
            }
        }

        if (i > 0) {
            if (i == 1) {
                QForgeMod.LOGGER.info("Added " + i + " ore to biome: " + event.getName());
            } else {
                QForgeMod.LOGGER.info("Added " + i + " ores to biome: " + event.getName());
            }
        }
    }
}
