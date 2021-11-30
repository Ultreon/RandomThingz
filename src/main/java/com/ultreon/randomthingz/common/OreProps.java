package com.ultreon.randomthingz.common;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.*;

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
    private ConfiguredPlacement<AtSurfaceWithExtraConfig> countExtra;
    @Deprecated
    private ConfiguredPlacement<FeatureSpreadConfig> countMultilayer;
    @Deprecated
    private ConfiguredPlacement<TopSolidRangeConfig> range;
    @Deprecated
    private ConfiguredPlacement<DepthAverageConfig> depthAverage;

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
        ConfiguredFeature<?, ?> feature = Feature.ORE.withConfiguration(new OreFeatureConfig(ruleTest.get(), blockState.get(), size));
        if (chance != null) {
            feature = feature.chance(chance);
        }

        if (count != null) {
            feature = feature.count(count);
        }

        if (square) {
            feature = feature.square();
        }

        if (countExtra != null) {
            feature = feature.withPlacement(countExtra);
        }

        if (countMultilayer != null) {
            feature = feature.withPlacement(countMultilayer);
        }

        if (range != null) {
            feature = feature.withPlacement(range);
        }

        if (depthAverage != null) {
            feature = feature.withPlacement(depthAverage);
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
            this.oreProps.countExtra = Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(count, extraChance, extraCount));
            return this;
        }

        @Deprecated
        public Builder countMultilayer(int base) {
            this.oreProps.countMultilayer = Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(base));
            return this;
        }

        @Deprecated
        public Builder square() {
            this.oreProps.square = true;
            return this;
        }

        @Deprecated
        public Builder range(int top) {
            this.oreProps.range = Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, top));
            return this;
        }

        @Deprecated
        public Builder range(int bottom, int top) {
            this.oreProps.range = Placement.RANGE.configure(new TopSolidRangeConfig(bottom, 0, top));
            return this;
        }

        @Deprecated
        public Builder range(int bottom, int topOffset, int top) {
            this.oreProps.range = Placement.RANGE.configure(new TopSolidRangeConfig(bottom, topOffset, top));
            return this;
        }

        @Deprecated
        public Builder depthAverage(int baseline, int spread) {
            this.oreProps.depthAverage = Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(baseline, spread));
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