package com.qtech.randomthingz.commons;

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

@Deprecated
public class OreProperties {
    @Deprecated private final Function<ConfiguredFeature<OreFeatureConfig, ?>, ConfiguredFeature<?, ?>> configFunction;
    @Deprecated private final RuleTest filler;
    @Deprecated private final int size;
    @Deprecated private Predicate<BiomeLoadingEvent> predicate;
    @Deprecated private ConfiguredFeature<?, ?> feature;

    @Deprecated
    public OreProperties(Function<ConfiguredFeature<OreFeatureConfig, ?>, ConfiguredFeature<?, ?>> function, RuleTest filler, int size) {
        this((b) -> true, function, filler, size);
    }

    @Deprecated
    public OreProperties(Predicate<BiomeLoadingEvent> predicate, Function<ConfiguredFeature<OreFeatureConfig, ?>, ConfiguredFeature<?, ?>> function, RuleTest filler, int size) {
        this.configFunction = function;
        this.filler = filler;
        this.predicate = predicate;
        this.size = size;
    }

    @Deprecated
    public void applyBlockIfNot(Block block) {
        if (this.feature == null) {
            this.feature = this.configFunction.apply(Feature.ORE.withConfiguration(new OreFeatureConfig(this.filler, block.getDefaultState(), this.size)));
        }
    }

    @Deprecated
    public void addBiome(Biome biome) {
        this.predicate = this.predicate.or((b) -> Objects.equals(b.getName(), biome.getRegistryName()));
    }

    @Deprecated
    public void addBiomes(Predicate<BiomeLoadingEvent> predicate) {
        this.predicate = this.predicate.or(predicate);
    }

    @Deprecated
    public final boolean testBiome(BiomeLoadingEvent biome) {
        return predicate.test(biome);
    }

    @Deprecated
    public ConfiguredFeature<?, ?> getFeature() {
        return feature;
    }
}
