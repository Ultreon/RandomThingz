package com.ultreon.randomthingz.world.gen.ores;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class ConfiguredFeatureQFM<FC extends IFeatureConfig, F extends Feature<FC>> extends ConfiguredFeature<FC, F> {
    private float chance = 1.0F;

    public ConfiguredFeatureQFM(F feature, FC config) {

        super(feature, config);
    }

    public ConfiguredFeatureQFM<FC, F> setChance(float chance) {

        this.chance = chance;
        return this;
    }

    @Override
    public boolean generate(@Nonnull ISeedReader reader, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random rand, @Nonnull BlockPos pos) {
        rand.nextFloat();
        if (rand.nextFloat() < chance) {
            return super.generate(reader, chunkGenerator, rand, pos);
        }
        return false;
    }

}