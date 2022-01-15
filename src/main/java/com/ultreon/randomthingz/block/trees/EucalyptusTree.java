package com.ultreon.randomthingz.block.trees;

import com.ultreon.randomthingz.world.gen.features.common.ModFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class EucalyptusTree extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(@NotNull Random randomIn, boolean largeHive) {
        return ModFeatures.EUCALYPTUS_TREE;
    }
}
