package com.ultreon.randomthingz.world.gen;

import com.ultreon.randomthingz.world.gen.placement.common.ModPlacedFeatures;
import lombok.experimental.UtilityClass;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;

/**
 * Biome generation class.
 *
 * @author Qboi123
 */
@UtilityClass
public class ModGeneration {
    public static void loadTrees(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.FOREST) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.EUCALYPTUS_TREE);
        }
    }

    public static void loadLakes(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.PLAINS) {
            event.getGeneration().addFeature(GenerationStep.Decoration.LAKES, ModPlacedFeatures.OIL_LAKE);
        }
        if (event.getCategory() == Biome.BiomeCategory.DESERT) {
            event.getGeneration().addFeature(GenerationStep.Decoration.LAKES, ModPlacedFeatures.OIL_LAKE);
        }
    }
}
