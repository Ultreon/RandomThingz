package com.qsoftware.forgemod.world.gen;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
//import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID)
@SuppressWarnings({"SameParameterValue", "unused", "RedundantSuppression"})
public class OreGen {
//    private static HashMap<Biome, > map = new HashMap<>();
    private static final ArrayList<HashMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>>> list = new ArrayList<>();
//    public static final OreFeatureConfig.FillerBlockType FILLER_ALL_BLOCKS = new TagMatchRuleTest("FILLER_ALL_BLOCKS", "qforgemod_filler_all_blocks", (blockState) -> blockState.getBlock() != Blocks.BEDROCK);

    public static void generateOres() {
        // Started generating ores.
        QForgeUtils.LOGGER.info("-===========- Generate Ores [START] -===========-");

        // Loop all biomes for creating ore features.
        for (Biome biome : ForgeRegistries.BIOMES) {
            createOreFeature(16, 5, 5, 64, 8, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.COPPER_ORE, biome);
            createOreFeature(13, 5, 5, 42, 3, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.STEEL_ORE, biome);
            createOreFeature(6, 5, 5, 23, 2, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.TUNGSTEN_ORE, biome);
            createOreFeature(10, 1, 1, 8, 5, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.URANIUM_ORE, biome);
            createOreFeature(6, 8, 3, 30, 3, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.RUBY_ORE, biome);
            createOreFeature(8, 8, 6, 36, 4, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.AMETHYST_ORE, biome);
            createOreFeature(4, 1, 1, 14, 2, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.TANZANITE_ORE, biome);
            createOreFeature(3, 8, 0, 11, 1, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.ULTRINIUM_ORE, biome);
            createOreFeature(1, 8, 0, 10, 1, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInit.INFINITY_ORE, biome);

            // Bottom obsidian noise.
            createOreFeature(4096, 1, 0, 1, 3, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.OBSIDIAN, biome);
            createOreFeature(2048, 2, 0, 2, 3, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.OBSIDIAN, biome);
            createOreFeature(1024, 3, 0, 3, 3, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.OBSIDIAN, biome);
            createOreFeature(512, 3, 0, 4, 3, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.OBSIDIAN, biome);
            createOreFeature(256, 5, 0, 5, 3, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.OBSIDIAN, biome);
            createOreFeature(128, 6, 0, 6, 3, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.OBSIDIAN, biome);
            createOreFeature(64, 7, 0, 7, 3, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.OBSIDIAN, biome);
        }

        // Ended generating ores.
        QForgeUtils.LOGGER.info("-===========- Generate Ores [ END ] -===========-");
    }

    protected static void createGemOreFeature(int count, int bottomOffset, int topOffset, int maximum, int size, RuleTest filler, Block ore, Biome[] biomes) {
        // Ore Feature
        BlockState blockState = ore.getDefaultState();
        OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(filler, blockState, size);

        // Configured Feature
        ConfiguredFeature<?, ?> configuredFeature = Feature.ORE.withConfiguration(oreFeatureConfig).range(maximum - topOffset).square().func_242731_b(bottomOffset);
        Features.ORE_DIAMOND

        // Add feature
        for (Biome biome : biomes) {
            QForgeUtils.LOGGER.info("Adding Ore Feature for: '" + ore.getRegistryName() + "' in biome: " + biome.getRegistryName());
            list.add(new HashMap.SimpleEntry<>(GenerationStage.Decoration.UNDERGROUND_ORES, configuredFeature));
        }
    }

    protected static void createOreFeature(int count, int bottomOffset, int topOffset, int maximum, int size, RuleTest filler, Block ore, Biome biome) {
        // Ore Feature
        BlockState blockState = ore.getDefaultState();
        OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(filler, blockState, size);

        // Configured Feature
        ConfiguredFeature<?, ?> configuredFeature = Feature.ORE.withConfiguration(oreFeatureConfig).func_242733_d(maximum - topOffset).func_242728_a().func_242731_b(bottomOffset);

        // Add feature
        QForgeUtils.LOGGER.info("Adding Ore Feature for: '" + ore.getRegistryName() + "' in biome: " + biome.getRegistryName());
        list.add(new HashMap.SimpleEntry<>(GenerationStage.Decoration.UNDERGROUND_ORES, configuredFeature));
    }

    @SubscribeEvent
    public void onBiomeLoading(BiomeLoadingEvent event) {
        for (AbstractMap.SimpleEntry<GenerationStage.Decoration, ConfiguredFeature<?, ?>> item : list) {
            event.getGeneration().withFeature(item.getKey(), item.getValue());
        }
    }
}
