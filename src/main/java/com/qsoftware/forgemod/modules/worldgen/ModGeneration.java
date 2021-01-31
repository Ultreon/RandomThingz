package com.qsoftware.forgemod.modules.worldgen;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.worldgen.ModFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Biome generation class.
 *
 * @author Qboi123
 */
public class ModGeneration {
    public static void loadTrees(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.FOREST) {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.EUCALYPTUS_TREE);
        }
    }

    public static void loadLakes(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.PLAINS) {
            event.getGeneration().withFeature(GenerationStage.Decoration.LAKES, ModFeatures.LAKE_OIL);
        }
        if (event.getCategory() == Biome.Category.DESERT) {
            event.getGeneration().withFeature(GenerationStage.Decoration.LAKES, ModFeatures.LAKE_OIL);
        }
    }
}
