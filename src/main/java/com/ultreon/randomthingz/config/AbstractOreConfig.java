package com.ultreon.randomthingz.config;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class AbstractOreConfig {
    @NotNull
    public abstract ConfiguredFeature<?, ?> getConfiguredFeature(Supplier<BlockState> state);
}
