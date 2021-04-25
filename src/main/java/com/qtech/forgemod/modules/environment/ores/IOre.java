package com.qtech.forgemod.modules.environment.ores;

import com.google.common.annotations.Beta;
import com.qtech.forgemod.modules.environment.ores.configs.IOreConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Collection;
import java.util.function.Predicate;

public interface IOre {
    // Generation of feature data.
    ConfiguredFeature<?, ?> generate();

    // Getters for values.
    int getHardness();
    int getHarvestLevel();

    // Config / features.
    IOreConfig getOreConfig();
    ConfiguredFeature<?, ?> getConfiguredFeature();

    // Misc getter for values.
    Block getOre();
    BlockState getFeatureState();
    @Beta Collection<Block> getGroundTypes();

    default Predicate<BiomeLoadingEvent> getBiomePredicate() {
        return (b) -> true;
    }
}
