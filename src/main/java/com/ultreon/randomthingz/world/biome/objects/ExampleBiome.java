package com.ultreon.randomthingz.world.biome.objects;

import com.ultreon.randomthingz.world.biome.BOPClimates;
import com.ultreon.randomthingz.world.biome.BiomeTemplate;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

/**
 * Example biome
 *
 * @author Qboi123
 */
public class ExampleBiome extends BiomeTemplate {
//    public Biome instance;
//    public BiomeGenerationSettings settings = new BiomeGenerationSettings.Builder()
//            .withSurfaceBuilder(ConfiguredSurfaceBuilders.GRASS)
//            .build();

    public ExampleBiome() {
        this.addWeight(BOPClimates.COOL_TEMPERATE, 5);
        this.setBeachBiome(Biomes.BEACH);
    }

    @Override
    protected void configureBiome(Biome.BiomeBuilder builder) {
        builder.precipitation(Biome.Precipitation.SNOW)
                .biomeCategory(Biome.BiomeCategory.PLAINS)
                .biomeCategory(Biome.BiomeCategory.PLAINS)
                .generationSettings(
                        new BiomeGenerationSettings.Builder()
                                .addCarver(GenerationStep.Carving.AIR,
                                        new ConfiguredWorldCarver<>(WorldCarver.CANYON,
                                                new CanyonCarverConfiguration(
                                                        new CarverConfiguration(68, UniformHeight.of(VerticalAnchor.absolute(66), VerticalAnchor.absolute(68)), ConstantFloat.of(5f), VerticalAnchor.absolute(62), CarverDebugSettings.DEFAULT),
                                                        ConstantFloat.of(0f),
                                                        new CanyonCarverConfiguration.CanyonShapeConfiguration(ConstantFloat.of(4f), ConstantFloat.of(.125f), 4, UniformFloat.of(5f, 9f), 5f, 2.5f)
                                                )
                                        )
                                )
                                .build()
                )
                .temperature(.8f).downfall(.4f);

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
//        // Structures
//        builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, StructureFeature.VILLAGE.configured(new JigsawConfiguration(() -> {
//            return PlainVillagePools.START;
//        }, 6)));
//        builder.withStructure(StructureFeatures.PILLAGER_OUTPOST);
//        builder.withStructure(StructureFeatures.RUINED_PORTAL);
//        builder.withStructure(StructureFeatures.MINESHAFT);
//        builder.withStructure(StructureFeatures.STRONGHOLD);
//        builder.withStructure(StructureFeatures.SWAMP_HUT);
//        builder.withStructure(StructureFeatures.BURIED_TREASURE);

        // Underground
        BiomeDefaultFeatures.addExtraGold(builder);
        BiomeDefaultFeatures.addExtraEmeralds(builder);
        BiomeDefaultFeatures.addMossyStoneBlock(builder);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultFlowers(builder);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addGroveTrees(builder);
        BiomeDefaultFeatures.addForestGrass(builder);
        BiomeDefaultFeatures.addForestFlowers(builder);
        BiomeDefaultFeatures.addPlainGrass(builder);
        BiomeDefaultFeatures.addPlainVegetation(builder);
        BiomeDefaultFeatures.addFerns(builder);
        BiomeDefaultFeatures.addCommonBerryBushes(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
    }

    @Override
    protected void configureMobSpawns(MobSpawnSettings.Builder builder) {
        // Entities
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 5, 2, 6));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 3));
        builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
        builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BOAT, 100, 4, 6));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 95, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 100, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 100, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1));
    }
}
