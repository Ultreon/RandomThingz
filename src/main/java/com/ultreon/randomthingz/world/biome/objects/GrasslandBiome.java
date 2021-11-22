package com.ultreon.randomthingz.world.biome.objects;

import com.ultreon.randomthingz.world.biome.BOPClimates;
import com.ultreon.randomthingz.world.biome.BiomeTemplate;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

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
    protected void configureBiome(Biome.Builder builder) {
        builder.precipitation(Biome.RainType.RAIN)
                .category(Biome.Category.PLAINS)
                .depth(0.1F)
                .scale(0.2F)
                .temperature(0.6F)
                .downfall(0.7F);
        builder.setEffects((new BiomeAmbience.Builder())
                .setWaterColor(4159204)
                .setWaterFogColor(329011)
                .setFogColor(12638463)
                .withSkyColor(calculateSkyColor(0.6F))
                .withGrassColor(0x88C57F)
                .withFoliageColor(0x6AB66F)
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
                .build());
    }

    @Override
    protected void configureGeneration(BiomeGenerationSettings.Builder builder) {
        builder.withSurfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_SAND_CONFIG));

        // Structures
        builder.withStructure(StructureFeatures.VILLAGE_PLAINS);
        builder.withStructure(StructureFeatures.PILLAGER_OUTPOST);
        builder.withStructure(StructureFeatures.RUINED_PORTAL);
        builder.withStructure(StructureFeatures.MINESHAFT);
        builder.withStructure(StructureFeatures.STRONGHOLD);
        builder.withStructure(StructureFeatures.SWAMP_HUT);
        builder.withStructure(StructureFeatures.BURIED_TREASURE);

        // Underground
//        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);

        ////////////////////////////////////////////////////////////
        // Vegetation
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUGAR_CANE);

        ////////////////////////////////////////////////////////////

        // Other Features
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
//        DefaultBiomeFeatures.freezingSurfaceFreezing(builder);
    }

    @Override
    protected void configureMobSpawns(MobSpawnInfo.Builder builder) {
        // Entities
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 10, 4, 4));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 10, 4, 4));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.HORSE, 5, 2, 6));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.DONKEY, 1, 1, 3));
        builder.withSpawner(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 10, 8, 8));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SPIDER, 100, 4, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE, 95, 4, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 100, 4, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.CREEPER, 100, 4, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 100, 4, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 10, 1, 4));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.WITCH, 5, 1, 1));
    }
}