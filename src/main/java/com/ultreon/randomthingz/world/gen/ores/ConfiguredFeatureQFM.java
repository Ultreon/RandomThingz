package com.ultreon.randomthingz.world.gen.ores;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ConfiguredFeatureQFM<FC extends FeatureConfiguration, F extends Feature<FC>> extends ConfiguredFeature<FC, F> {
    private float chance = 1.0f;

    public ConfiguredFeatureQFM(F feature, FC config) {

        super(feature, config);
    }

    public ConfiguredFeatureQFM<FC, F> setChance(float chance) {

        this.chance = chance;
        return this;
    }

    @Override
    public boolean place(@NotNull WorldGenLevel reader, @NotNull ChunkGenerator chunkGenerator, @NotNull Random rand, @NotNull BlockPos pos) {
        rand.nextFloat();
        if (rand.nextFloat() < chance) {
            return super.place(reader, chunkGenerator, rand, pos);
        }
        return false;
    }

}