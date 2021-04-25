/* ******************************************************************************
 * Copyright 2014-2019, the Biomes O' Plenty Team
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 *
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 * *****************************************************************************/
package com.qtech.forgemod.modules.environment.biome;

import lombok.Setter;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;

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
    @Setter(PROTECTED) private RegistryKey<Biome> beachBiome = Biomes.BEACH;
    @Setter(PROTECTED) private RegistryKey<Biome> riverBiome = Biomes.RIVER;
    @Setter(PROTECTED) BiFunction<Double, Double, Integer> foliageColorFunction;
    @Setter(PROTECTED) private BiFunction<Double, Double, Integer> grassColorFunction;
    @Setter(PROTECTED) private BiFunction<Double, Double, Integer> waterColorFunction;

    public static int calculateSkyColor(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }

    protected void configureBiome(Biome.Builder builder) {
    }

    protected void configureGeneration(BiomeGenerationSettings.Builder builder) {
    }

    protected void configureMobSpawns(MobSpawnInfo.Builder builder) {
    }

    protected void configureDefaultMobSpawns(MobSpawnInfo.Builder builder) {
        builder.isValidSpawnBiomeForPlayer();
    }

    public final Biome build() {
        Biome.Builder biomeBuilder = new Biome.Builder();

        // Configure the biome generation
        BiomeGenerationSettings.Builder biomeGenBuilder = new BiomeGenerationSettings.Builder();
        this.configureGeneration(biomeGenBuilder);
        biomeBuilder.withGenerationSettings(biomeGenBuilder.build());

        // Configure mob spawning
        MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();
        this.configureDefaultMobSpawns(mobSpawnBuilder);
        this.configureMobSpawns(mobSpawnBuilder);
        biomeBuilder.withMobSpawnSettings(mobSpawnBuilder.build());

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
