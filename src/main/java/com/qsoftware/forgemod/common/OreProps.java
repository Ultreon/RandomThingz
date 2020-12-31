package com.qsoftware.forgemod.common;

import net.minecraft.block.BlockState;
import net.minecraft.crash.ReportedException;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.*;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class OreProps implements Cloneable {
    // ConfiguredFeature
    private boolean square = false;
    private Integer chance;
    private Integer count;
    private Integer size;
    private ConfiguredPlacement<AtSurfaceWithExtraConfig> countExtra;
    private ConfiguredPlacement<FeatureSpreadConfig> countMultilayer;
    private ConfiguredPlacement<TopSolidRangeConfig> range;
    private ConfiguredPlacement<DepthAverageConfig> depthAverage;

    // OreFeatureConfig
    private Supplier<RuleTest> ruleTest;
    private Supplier<BlockState> blockState;

    public static Builder builder() {
        return new Builder();
    }

    public ConfiguredFeature<?, ?> getConfiguredFeature() {
        ConfiguredFeature<?, ?> feature = Feature.ORE.withConfiguration(new OreFeatureConfig(ruleTest.get(), blockState.get(), size));
        if (chance != null) {
            feature = feature.chance(chance);
        }

        if (count != null) {
            feature = feature.func_242731_b(count);
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

    @Override
    public OreProps clone() {
        try {
            return (OreProps) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Builder {
        private final OreProps oreProps;

        Builder() {
            oreProps = new OreProps();
        }

        public Builder ruleTest(Supplier<RuleTest> ruleTest) {
            this.oreProps.ruleTest = ruleTest;
            return this;
        }

        public Builder chance(int chance) {
            this.oreProps.chance = chance;
            return this;
        }

        public Builder count(int count) {
            this.oreProps.count = count;
            return this;
        }

        public Builder countExtra(int count, int extraChance, int extraCount) {
            this.oreProps.countExtra = Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(count, extraChance, extraCount));
            return this;
        }

        public Builder countMultilayer(int base) {
            this.oreProps.countMultilayer = Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(base));
            return this;
        }

        public Builder square() {
            this.oreProps.square = true;
            return this;
        }

        public Builder range(int top) {
            this.oreProps.range = Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, top));
            return this;
        }

        public Builder range(int bottom, int top) {
            this.oreProps.range = Placement.RANGE.configure(new TopSolidRangeConfig(bottom, 0, top));
            return this;
        }

        public Builder range(int bottom, int topOffset, int top) {
            this.oreProps.range = Placement.RANGE.configure(new TopSolidRangeConfig(bottom, topOffset, top));
            return this;
        }

        public Builder depthAverage(int baseline, int spread) {
            this.oreProps.depthAverage = Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(baseline, spread));
            return this;
        }

        public Builder blockState(Supplier<BlockState> blockState) {
            this.oreProps.blockState = blockState;
            return this;
        }

        public Builder size(int size) {
            this.oreProps.size = size;
            return this;
        }

        public OreProps build() {
            return oreProps;
        }
    }
}