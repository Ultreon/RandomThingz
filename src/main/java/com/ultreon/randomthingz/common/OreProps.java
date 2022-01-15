package com.ultreon.randomthingz.common;

import net.minecraft.world.gen.placement.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import net.minecraft.world.level.levelgen.placement.DepthAverageConfigation;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.function.Supplier;

@Deprecated
public class OreProps implements Cloneable {
    // ConfiguredFeature
    @Deprecated
    private boolean square = false;
    @Deprecated
    private Integer chance;
    @Deprecated
    private Integer count;
    @Deprecated
    private Integer size;
    @Deprecated
    private ConfiguredDecorator<FrequencyWithExtraChanceDecoratorConfiguration> countExtra;
    @Deprecated
    private ConfiguredDecorator<CountConfiguration> countMultilayer;
    @Deprecated
    private ConfiguredDecorator<RangeDecoratorConfiguration> range;
    @Deprecated
    private ConfiguredDecorator<DepthAverageConfigation> depthAverage;

    // OreFeatureConfig
    @Deprecated
    private Supplier<RuleTest> ruleTest;
    @Deprecated
    private Supplier<BlockState> blockState;

    @Deprecated
    public static Builder builder() {
        return new Builder();
    }

    @Deprecated
    public ConfiguredFeature<?, ?> getConfiguredFeature() {
        ConfiguredFeature<?, ?> feature = Feature.ORE.configured(new OreConfiguration(ruleTest.get(), blockState.get(), size));
        if (chance != null) {
            feature = feature.chance(chance);
        }

        if (count != null) {
            feature = feature.count(count);
        }

        if (square) {
            feature = feature.squared();
        }

        if (countExtra != null) {
            feature = feature.decorated(countExtra);
        }

        if (countMultilayer != null) {
            feature = feature.decorated(countMultilayer);
        }

        if (range != null) {
            feature = feature.decorated(range);
        }

        if (depthAverage != null) {
            feature = feature.decorated(depthAverage);
        }

        return feature;
    }

    @Deprecated
    @Override
    public OreProps clone() {
        try {
            return (OreProps) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static class Builder {
        @Deprecated
        private final OreProps oreProps;

        @Deprecated
        Builder() {
            oreProps = new OreProps();
        }

        @Deprecated
        public Builder ruleTest(Supplier<RuleTest> ruleTest) {
            this.oreProps.ruleTest = ruleTest;
            return this;
        }

        @Deprecated
        public Builder chance(int chance) {
            this.oreProps.chance = chance;
            return this;
        }

        @Deprecated
        public Builder count(int count) {
            this.oreProps.count = count;
            return this;
        }

        @Deprecated
        public Builder countExtra(int count, int extraChance, int extraCount) {
            this.oreProps.countExtra = FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(count, extraChance, extraCount));
            return this;
        }

        @Deprecated
        public Builder countMultilayer(int base) {
            this.oreProps.countMultilayer = FeatureDecorator.COUNT_MULTILAYER.configured(new CountConfiguration(base));
            return this;
        }

        @Deprecated
        public Builder square() {
            this.oreProps.square = true;
            return this;
        }

        @Deprecated
        public Builder range(int top) {
            this.oreProps.range = FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(0, 0, top));
            return this;
        }

        @Deprecated
        public Builder range(int bottom, int top) {
            this.oreProps.range = FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(bottom, 0, top));
            return this;
        }

        @Deprecated
        public Builder range(int bottom, int topOffset, int top) {
            this.oreProps.range = FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(bottom, topOffset, top));
            return this;
        }

        @Deprecated
        public Builder depthAverage(int baseline, int spread) {
            this.oreProps.depthAverage = FeatureDecorator.DEPTH_AVERAGE.configured(new DepthAverageConfigation(baseline, spread));
            return this;
        }

        @Deprecated
        public Builder blockState(Supplier<BlockState> blockState) {
            this.oreProps.blockState = blockState;
            return this;
        }

        @Deprecated
        public Builder size(int size) {
            this.oreProps.size = size;
            return this;
        }

        @Deprecated
        public OreProps build() {
            return oreProps;
        }
    }
}