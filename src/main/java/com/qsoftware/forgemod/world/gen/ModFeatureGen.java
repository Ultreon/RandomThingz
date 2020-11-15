package com.qsoftware.forgemod.world.gen;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.ModFeatures;
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
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID)
public class ModFeatureGen {
    @SubscribeEvent
    public static void generateTrees(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.FOREST) {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.EUCALYPTUS_TREE);
        }
    }
}
