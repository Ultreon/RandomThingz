/* ******************************************************************************
 * Copyright 2014-2019, the Biomes O' Plenty Team
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 *
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 * *****************************************************************************/
package com.ultreon.randomthingz.world.biome;

import lombok.Setter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static lombok.AccessLevel.PROTECTED;

/**
 * Biome template class.
 *
 * @author Biomes 'o Plenty mod.
 */
public class BiomeTemplate {
    private final Map<BOPClimates, Integer> weightMap = new HashMap<>();
    @Setter(PROTECTED)
    private ResourceKey<Biome> beachBiome = Biomes.BEACH;
    @Setter(PROTECTED)
    private ResourceKey<Biome> riverBiome = Biomes.RIVER;
    @Setter(PROTECTED)
    BiFunction<Double, Double, Integer> foliageColorFunction;
    @Setter(PROTECTED)
    private BiFunction<Double, Double, Integer> grassColorFunction;
    @Setter(PROTECTED)
    private BiFunction<Double, Double, Integer> waterColorFunction;

    public static int calculateSkyColor(float temperature) {
        float lvt_1_1_ = temperature / 3.0f;
        lvt_1_1_ = Mth.clamp(lvt_1_1_, -1.0f, 1.0f);
        return Mth.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0f);
    }

    protected void configureBiome(Biome.BiomeBuilder builder) {
    }

    protected void configureGeneration(BiomeGenerationSettings.Builder builder) {
    }

    protected void configureMobSpawns(MobSpawnSettings.Builder builder) {
    }

    protected void configureDefaultMobSpawns(MobSpawnSettings.Builder builder) {
        builder.setPlayerCanSpawn();
    }

    public final Biome build() {
        Biome.BiomeBuilder biomeBuilder = new Biome.BiomeBuilder();

        // Configure the biome generation
        BiomeGenerationSettings.Builder biomeGenBuilder = new BiomeGenerationSettings.Builder();
        this.configureGeneration(biomeGenBuilder);
        biomeBuilder.generationSettings(biomeGenBuilder.build());

        // Configure mob spawning
        MobSpawnSettings.Builder mobSpawnBuilder = new MobSpawnSettings.Builder();
        this.configureDefaultMobSpawns(mobSpawnBuilder);
        this.configureMobSpawns(mobSpawnBuilder);
        biomeBuilder.mobSpawnSettings(mobSpawnBuilder.build());

        // Configure and build the biome
        this.configureBiome(biomeBuilder);
        return biomeBuilder.build();
    }

    public final BiomeMetadata buildMetadata() {
        return new BiomeMetadata(this.weightMap, this.beachBiome, this.riverBiome, this.foliageColorFunction, this.grassColorFunction, this.waterColorFunction);
    }

    public void addWeight(BOPClimates climate, int weight) {
        this.weightMap.put(climate, weight);
    }
}
