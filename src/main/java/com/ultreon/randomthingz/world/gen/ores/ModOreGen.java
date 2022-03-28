package com.ultreon.randomthingz.world.gen.ores;

import com.mojang.datafixers.util.Pair;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.init.ModBlocks;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import com.ultreon.randomthingz.world.gen.ores.configs.DefaultOreConfig;
import lombok.Getter;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Ore generator class.
 *
 * @author Qboi123
 */
public final class ModOreGen {
    private static final ArrayList<Pair<Predicate<BiomeLoadingEvent>, PlacedFeature>> ores = new ArrayList<>();

    @Getter
    private static final ModOreGen instance = new ModOreGen();

    private ModOreGen() {
    }

    @Deprecated
    @SuppressWarnings("ALL")
    public static class OreProps {
        private final PlacedFeature feature;

        public OreProps(PlacedFeature feature) {
            this.feature = feature;
        }

        public OreProps chance(int chance) {
//            feature.placed();
            return this;
        }

        public OreProps count(int count) {
//            feature.count(count);
            return this;
        }

        public OreProps countExtra(int count, int extraChance, int extraCount) {
//            feature.decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(count, extraChance, extraCount)));
            return this;
        }

        public OreProps countMultilayer(int base) {
//            feature.decorated(FeatureDecorator.COUNT_MULTILAYER.configured(new CountConfiguration(base)));
            return this;
        }

        public OreProps square() {
//            feature.squared();
            return this;
        }

        public OreProps range(int top) {
//            feature.range(top);
            return this;
        }

        public OreProps range(int top, int bottom) {
//            feature.decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(bottom, 0, top)));
            return this;
        }

        public OreProps range(int top, int topOffset, int bottom) {
//            feature.decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(bottom, topOffset, top)));
            return this;
        }

        public OreProps depthAverage(int baseline, int spread) {
//            feature.decorated(FeatureDecorator.DEPTH_AVERAGE.configured(new DepthAverageConfigation(baseline, spread)));
            return this;
        }
    }

    /**
     * Create ore features.
     *
     * @see #loadOreFeatures(BiomeLoadingEvent)
     */
    public void addOresFeatures() {
        // Started generating ores.
        RandomThingz.LOGGER.info("-===========- Create Ore Features [START] -===========-");

        addOreFeature(new GenericOre("gilded_dirt",
                ModBlocks.GILDED_DIRT::asBlock,
                new TagMatchTest(BlockTags.DIRT),
                .5f, .5f,
                ToolType.SHOVEL, ToolRequirement.IRON,
                new DefaultOreConfig(12, 6, 30, 48),
                (b) -> b.getCategory() == Biome.BiomeCategory.RIVER
        ));
//        addOreFeature((a) -> a.count(6).range(25, 8), 6, BASE_STONE_OVERWORLD, ModBlocks.RUBY_ORE.get(),
//                (b) -> b.getCategory() == Biome.Category.EXTREME_HILLS
//        );
//        addOreFeature((a) -> a.count(8).range(30, 8), 8, BASE_STONE_OVERWORLD, ModBlocks.AMETHYST_ORE.get(),
//                (b) -> b.getCategory() == Biome.Category.EXTREME_HILLS
//        );
//        addOreFeature((a) -> a.count(8).range(30, 8), 8, BASE_STONE_OVERWORLD, ModBlocks.MALACHITE_ORE.get(),
//                (b) -> b.getCategory() == Biome.Category.EXTREME_HILLS || b.getCategory() == Biome.Category.DESERT
//        );
//        addOreFeature((a) -> a.count(8).range(30, 8), 8, BASE_STONE_OVERWORLD, ModBlocks.PERIDOT_ORE.get(),
//                (b) -> b.getCategory() == Biome.Category.EXTREME_HILLS || b.getCategory() == Biome.Category.DESERT
//        );
//        addOreFeature((a) -> a.count(4).range(15, 1), 2, BASE_STONE_OVERWORLD, ModBlocks.TANZANITE_ORE.get(),
//                (b) -> b.getClimate().temperature >= 1f
//        );
//        addOreFeature((a) -> a.count(2).range(11, 8), 1, BASE_STONE_OVERWORLD, ModBlocks.ULTRINIUM_ORE.get(),
//                (b) -> b.getClimate().temperature >= 1f
//        );
//        addOreFeature((a) -> a.chance(128).count(1).range(48, 0), 1, BASE_STONE_OVERWORLD, ModBlocks.INFINITY_ORE.get(),
//                (b) -> b.getClimate().temperature >= 1f
//        );

        for (ItemMaterialOre ore : ItemMaterialOre.values()) {
            addOreFeature(ore);
        }

        // Ended generating ores.
        RandomThingz.LOGGER.info("-===========- Create Ore Features [ END ] -===========-");
    }

//    public static void addOreFeature(Function<OreProps, OreProps> oreProps, int size, RuleTest filler, Block ore, Predicate<BiomeLoadingEvent> biome) {
//        // Ore Feature
//        BlockState blockState = ore.defaultBlockState();
//        OreConfiguration oreFeatureConfig = new OreConfiguration(filler, blockState, size);
//
//        // Configured Feature
//        ConfiguredFeature<?, ?> configuredFeature = oreProps.apply(new OreProps(Feature.ORE.configured(oreFeatureConfig))).feature;
//
//        // Add ore to list.
//        ores.add(new Pair<>(biome, configuredFeature));
//    }

    private static void addOreFeature(Ore ore) {
        // Get configured feature.
        PlacedFeature configuredFeature = ore.getPlacedFeature();
        Predicate<BiomeLoadingEvent> predicate = ore.getBiomePredicate();

        // Add ore to list.
        ores.add(new Pair<>(predicate, configuredFeature));
    }

    /**
     * In this method ores will be added to specified biomes, the method is called when biomes are loading.
     *
     * @param event event for biome loading.
     */
    @SuppressWarnings("CommentedOutCode")
    @SubscribeEvent
    public void loadOreFeatures(BiomeLoadingEvent event) {
        int i = 0;
//        if (oreMap.containsKey(event.getName())) {
//            ArrayList<AbstractMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>>> list = oreMap.get(event.getName());
//            for (AbstractMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>> item : list) {
//                event.getGeneration().withFeature(item.getKey(), item.getValue());
//                i++;
//            }
//        }

        for (Pair<Predicate<BiomeLoadingEvent>, PlacedFeature> ore : ores) {
            if (ore.getFirst().test(event)) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ore.getSecond());
                i++;
            }
        }

        if (i > 0) {
            if (i == 1) {
                RandomThingz.LOGGER.info("Added " + i + " ore to biome: " + event.getName());
            } else {
                RandomThingz.LOGGER.info("Added " + i + " ores to biome: " + event.getName());
            }
        }
    }
}
