package com.ultreon.randomthingz.world.biome.objects;

import com.ultreon.randomthingz.world.biome.BOPClimates;
import com.ultreon.randomthingz.world.biome.BiomeTemplate;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.CountPlacement;

/**
 * Grasslands biome class.
 *
 * @author Biomes 'o Plenty mod.
 */
public class GrasslandBiome extends BiomeTemplate {
    public GrasslandBiome() {
        this.addWeight(BOPClimates.COOL_TEMPERATE, 5);
        this.setBeachBiome(Biomes.BEACH);
    }

    @Override
    protected void configureBiome(Biome.BiomeBuilder builder) {
        builder.precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.PLAINS)
                .generationSettings(
                        new BiomeGenerationSettings.Builder()
                                .addCarver(GenerationStep.Carving.AIR,
                                        new ConfiguredWorldCarver<>(WorldCarver.CANYON,
                                                new CanyonCarverConfiguration(
                                                        new CarverConfiguration(68, UniformHeight.of(VerticalAnchor.absolute(66), VerticalAnchor.absolute(68)), ConstantFloat.of(.2f), VerticalAnchor.absolute(62), CarverDebugSettings.DEFAULT),
                                                        ConstantFloat.of(0f),
                                                        new CanyonCarverConfiguration.CanyonShapeConfiguration(ConstantFloat.of(4f), ConstantFloat.of(.1f), 9, UniformFloat.of(7f, 10f), 5f, 2.5f)
                                                )
                                        )
                                )
                                .build()
                )
                .temperature(.6f).downfall(.7f);
        builder.specialEffects((new BiomeSpecialEffects.Builder())
                .waterColor(4159204)
                .waterFogColor(329011)
                .fogColor(12638463)
                .skyColor(calculateSkyColor(.6f))
                .grassColorOverride(0x88C57F)
                .foliageColorOverride(0x6AB66F)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build());
    }

    @Override
    protected void configureGeneration(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStep.Carving.AIR,
                        new ConfiguredWorldCarver<>(WorldCarver.CANYON,
                                new CanyonCarverConfiguration(
                                        new CarverConfiguration(68, UniformHeight.of(VerticalAnchor.absolute(66), VerticalAnchor.absolute(68)), ConstantFloat.of(5f), VerticalAnchor.absolute(62), CarverDebugSettings.DEFAULT),
                                        ConstantFloat.of(0f),
                                        new CanyonCarverConfiguration.CanyonShapeConfiguration(ConstantFloat.of(4f), ConstantFloat.of(.125f), 4, UniformFloat.of(5f, 9f), 5f, 2.5f)
                                )
                        )
                );

        // Structures
//        builder.withStructure(StructureFeatures.VILLAGE_PLAINS);
//        builder.withStructure(StructureFeatures.PILLAGER_OUTPOST);
//        builder.withStructure(StructureFeatures.RUINED_PORTAL);
//        builder.withStructure(StructureFeatures.MINESHAFT);
//        builder.withStructure(StructureFeatures.STRONGHOLD);
//        builder.withStructure(StructureFeatures.SWAMP_HUT);
//        builder.withStructure(StructureFeatures.BURIED_TREASURE);

        // Underground
//        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addLushCavesSpecialOres(builder);
        BiomeDefaultFeatures.addLushCavesVegetationFeatures(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);

        ////////////////////////////////////////////////////////////
        // Vegetation
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationFeatures.PATCH_SUGAR_CANE.placed(CountPlacement.of(5)));
        ////////////////////////////////////////////////////////////
    }

    @Override
    protected void configureMobSpawns(MobSpawnSettings.Builder builder) {
        BiomeDefaultFeatures.plainsSpawns(builder);
    }
}