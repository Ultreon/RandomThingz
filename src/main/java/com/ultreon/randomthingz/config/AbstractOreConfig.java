package com.ultreon.randomthingz.config;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public abstract class AbstractOreConfig {
    @Nonnull
    public abstract ConfiguredFeature<?, ?> getConfiguredFeature(Supplier<BlockState> state);
}
