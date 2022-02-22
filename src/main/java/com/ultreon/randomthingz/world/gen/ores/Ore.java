package com.ultreon.randomthingz.world.gen.ores;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.world.gen.ores.configs.OreConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Collection;
import java.util.function.Predicate;

public interface Ore {
    // Generation of feature data.
    PlacedFeature generate();

    // Getters for values.
    float getHardness();

    // Config / features.
    OreConfig getOreConfig();

    PlacedFeature getPlacedFeature();

    // Misc getter for values.
    Block getStoneOre();

    Block getDeepslateOre();

    BlockState getStoneFeatureState();

    BlockState getDeepslateFeatureState();

    @Beta
    Collection<Block> getGroundTypes();

    default Predicate<BiomeLoadingEvent> getBiomePredicate() {
        return (b) -> true;
    }

    float getResistance();

    ToolRequirement getToolRequirement();
}
