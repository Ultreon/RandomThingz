package com.qtech.randomthingz.config;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public abstract class AbstractOreConfig {
    @Nonnull
    public abstract ConfiguredFeature<?, ?> getConfiguredFeature(Supplier<BlockState> state);
}