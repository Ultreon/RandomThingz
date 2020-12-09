package com.qsoftware.forgemod.world.gen;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Ore generator class.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID)
@SuppressWarnings({"SameParameterValue", "unused", "RedundantSuppression"})
public class ModOreGen {
    //    private static HashMap<Biome, > map = new HashMap<>();
    private static final ConcurrentHashMap<ResourceLocation, ArrayList<HashMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>>>> oreMap = new ConcurrentHashMap<>();
//    public static final OreFeatureConfig.FillerBlockType FILLER_ALL_BLOCKS = new TagMatchRuleTest("FILLER_ALL_BLOCKS", "qforgemod_filler_all_blocks", (blockState) -> blockState.getBlock() != Blocks.BEDROCK);

    public static void createOresFeatures() {
        // Started generating ores.
        QForgeMod.LOGGER.info("-===========- Create Ore Features [START] -===========-");

        // Loop all biomes for creating ore features.
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == Biome.Category.RIVER) {
                createOreFeature(12, 48, 0, 64, 6, new BlockMatchRuleTest(Blocks.DIRT), ModBlocks.GILDED_DIRT.get(), biome);
            }

            if (biome.getCategory() == Biome.Category.EXTREME_HILLS || biome.getCategory() == Biome.Category.DESERT) {
                createOreFeature(6, 8, 3, 30, 3, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.RUBY_ORE.get(), biome);
                createOreFeature(8, 8, 6, 36, 4, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.AMETHYST_ORE.get(), biome);
            }

            if (biome.getTemperature() >= 1.0f) {
                createOreFeature(4, 1, 1, 14, 2, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.TANZANITE_ORE.get(), biome);
            }

            if (biome.getCategory() == Biome.Category.MUSHROOM) {
                createOreFeature(3, 8, 0, 11, 1, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.ULTRINIUM_ORE.get(), biome);
                createOreFeature(1, 8, 0, 10, 1, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.INFINITY_ORE.get(), biome);
            }
        }

        // Ended generating ores.
        QForgeMod.LOGGER.info("-===========- Create Ore Features [ END ] -===========-");
    }

    protected static void createOreFeature(int count, int bottomOffset, int topOffset, int maximum, int size, RuleTest filler, Block ore, Biome[] biomes) {
        // Ore Feature
        BlockState blockState = ore.getDefaultState();
        OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(filler, blockState, size);

        // Configured Feature
        ConfiguredFeature<?, ?> configuredFeature = Feature.ORE.withConfiguration(oreFeatureConfig).range(maximum - topOffset).square().func_242731_b(bottomOffset);

        // Add feature
        for (Biome biome : biomes) {
            QForgeMod.LOGGER.info("Adding Ore Mapping for: '" + ore.getRegistryName() + "' to biome: " + biome.getRegistryName());
            addOreMapping(biome.getRegistryName(), new HashMap.SimpleEntry<>(GenerationStage.Decoration.UNDERGROUND_ORES, configuredFeature));
        }
    }

    protected static void createOreFeature(int count, int bottomOffset, int topOffset, int maximum, int size, RuleTest filler, Block ore, Biome biome) {
        // Ore Feature
        BlockState blockState = ore.getDefaultState();
        OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(filler, blockState, size);

        // Configured Feature
        ConfiguredFeature<?, ?> configuredFeature = Feature.ORE.withConfiguration(oreFeatureConfig).range(maximum - topOffset).square().func_242731_b(bottomOffset);

        // Add feature
        QForgeMod.LOGGER.info("Adding Ore Mapping for: '" + ore.getRegistryName() + "' in biome: " + biome.getRegistryName());
        addOreMapping(biome.getRegistryName(), new HashMap.SimpleEntry<>(GenerationStage.Decoration.UNDERGROUND_ORES, configuredFeature));
    }

    private static void addOreMapping(ResourceLocation registryName, AbstractMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>> configEntry) {
        if (!oreMap.containsKey(registryName)) {
            oreMap.put(registryName, new ArrayList<>());
        }

        oreMap.get(registryName).add(configEntry);
    }

    @SubscribeEvent
    public void onBiomeLoading(BiomeLoadingEvent event) {
        if (oreMap.containsKey(event.getName())) {
            ArrayList<AbstractMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>>> list = oreMap.get(event.getName());
            for (AbstractMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>> item : list) {
                event.getGeneration().withFeature(item.getKey(), item.getValue());
            }
        }
    }
}
