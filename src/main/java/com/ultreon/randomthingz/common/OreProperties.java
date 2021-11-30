package com.ultreon.randomthingz.common;

import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class OreProperties {
    private final Function<ConfiguredFeature<OreFeatureConfig, ?>, ConfiguredFeature<?, ?>> configFunction;
    private final RuleTest filler;
    private final int size;
    private Predicate<BiomeLoadingEvent> predicate;
    private ConfiguredFeature<?, ?> feature;

    public OreProperties(Function<ConfiguredFeature<OreFeatureConfig, ?>, ConfiguredFeature<?, ?>> function, RuleTest filler, int size) {
        this((b) -> true, function, filler, size);
    }

    public OreProperties(Predicate<BiomeLoadingEvent> predicate, Function<ConfiguredFeature<OreFeatureConfig, ?>, ConfiguredFeature<?, ?>> function, RuleTest filler, int size) {
        this.configFunction = function;
        this.filler = filler;
        this.predicate = predicate;
        this.size = size;
    }

    public void applyBlockIfNot(Block block) {
        if (this.feature == null) {
            this.feature = this.configFunction.apply(Feature.ORE.withConfiguration(new OreFeatureConfig(this.filler, block.getDefaultState(), this.size)));
        }
    }

    public void addBiome(Biome biome) {
        this.predicate = this.predicate.or((b) -> Objects.equals(b.getName(), biome.getRegistryName()));
    }

    public void addBiomes(Predicate<BiomeLoadingEvent> predicate) {
        this.predicate = this.predicate.or(predicate);
    }

    public final boolean testBiome(BiomeLoadingEvent biome) {
        return predicate.test(biome);
    }

    public ConfiguredFeature<?, ?> getFeature() {
        return feature;
    }
}
