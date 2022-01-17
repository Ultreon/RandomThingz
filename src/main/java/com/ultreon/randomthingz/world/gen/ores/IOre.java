package com.ultreon.randomthingz.world.gen.ores;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.world.gen.ores.configs.IOreConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
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

    @Beta
    Collection<Block> getGroundTypes();

    default Predicate<BiomeLoadingEvent> getBiomePredicate() {
        return (b) -> true;
    }

    float getResistance();

    ToolRequirement getToolRequirement();
}
